package com.becker.beckerSkillCinema.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentSearchBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.allFilmsByCategory.allFilmAdapters.AllFilmAdapter
import com.becker.beckerSkillCinema.presentation.home.allFilmsByCategory.allFilmAdapters.FilmsByFilterPagingSource
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()
    private var adapter: AllFilmAdapter by autoCleared()
    private var isEditFocused = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setSearchString()
        getFilmList()
    }

    private fun setAdapter() {
        adapter = AllFilmAdapter { onFilmClick(it) }

        binding.searchFilmList.layoutManager =
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )

        binding.searchFilmList.adapter = adapter

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.searchFilmList.smoothScrollToPosition(0)
                }
            }
        })

        adapter.addLoadStateListener { state ->
            val currentState = state.refresh
            binding.searchFilmList.isVisible = currentState != LoadState.Loading
            binding.loadingProgress.isVisible = currentState == LoadState.Loading
            binding.searchProgressText.isVisible = currentState != LoadState.Loading
            binding.searchProgressImage.isVisible = currentState == LoadState.Loading
            when (currentState) {
                is LoadState.Loading -> {
                    binding.searchFilmList.isVisible = false
                    binding.searchProgressGroup.isVisible = true
                    binding.searchProgressText.isVisible = false
                    binding.searchProgressImage.isVisible = true
                }
                is LoadState.NotLoading -> {
                    binding.searchFilmList.isVisible = true
                    binding.loadingProgress.isVisible = false
                    binding.searchProgressText.isVisible = false
                    binding.searchProgressImage.isVisible = false

                }
                else -> {
                    binding.searchFilmList.isVisible = false
                    binding.loadingProgress.isVisible = false
                    binding.searchProgressText.isVisible = true
                    binding.searchProgressImage.isVisible = true
                }
            }
        }
    }

    private fun onFilmClick(filmId: Int) {
        viewModel.putFilmId(filmId)
        val action =
            SearchFragmentDirections.actionFragmentSearchToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    private fun setSearchString() {
        binding.searchFilterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentSearch_to_searchSettingsFragment)
        }

        binding.searchMyField.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                binding.searchGroup.background =
                    if (hasFocus) {
                        isEditFocused = true
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.search_input_field_select,
                            null
                        )
                    } else {
                        ResourcesCompat.getDrawable(resources, R.drawable.search_input_field, null)
                    }
            }

        binding.searchMyField.setText(viewModel.getFilters().keyword)
        binding.searchMyField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    try {
                        delay(2000)
                        viewModel.updateFilters(
                            filterFilm = viewModel.getFilters().copy(keyword = s.toString())
                        )
                    } catch (e: Throwable) {
                        Timber.e("onTextChanged $e")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getFilmList() {
        viewLifecycleOwner.lifecycleScope.launch (Dispatchers.IO){
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.films.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFilterChanged.collect {
                    if (it) adapter.refresh()
                }
            }
        }
    }
}