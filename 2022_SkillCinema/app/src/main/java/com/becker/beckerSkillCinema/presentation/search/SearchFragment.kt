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
import com.becker.beckerSkillCinema.presentation.search.adapters.SearchAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()
    private var adapter: SearchAdapter by autoCleared()
    private var isEditFocused = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setSearchString()
        getFilmList()
    }

    private fun setAdapter() {
        adapter = SearchAdapter { onFilmClick(it) }

        binding.searchFilmList.layoutManager =
            GridLayoutManager(
                requireContext(),
                1,
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
            when (state.refresh) {
                is LoadState.Loading -> {
                    binding.apply {
                        searchFilmList.isVisible = false
                        searchProgressGroup.isVisible = true
                        loadingProgress.isVisible = true
                        searchProgressText.isVisible = false
                        searchProgressImage.isVisible = true
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        searchFilmList.isVisible = true
                        loadingProgress.isVisible = false
                        searchProgressText.isVisible = false
                        searchProgressImage.isVisible = false
                    }
                }
                else -> {
                    binding.apply {
                        searchFilmList.isVisible = false
                        loadingProgress.isVisible = false
                        searchProgressText.isVisible = true
                        searchProgressImage.isVisible = true
                    }
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
        binding.apply {
            searchFilterBtn.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentSearch_to_searchSettingsFragment)
            }
            searchMyField.onFocusChangeListener =
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
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.search_input_field,
                                null
                            )
                        }
                }

        }

        binding.searchMyField.setText(viewModel.getFilters().keyword)
        binding.searchMyField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    try {
                        delay(2000)
                        if (s.toString() != viewModel.getFilters().keyword) {
                            viewModel.updateFilters(
                                filterFilm = viewModel.getFilters().copy(keyword = s.toString())
                            )
                            adapter.refresh()
                        }
                    } catch (e: Throwable) {
                        Timber.e("onTextChanged $e")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getFilmList() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.pagedFilms?.collect {
                        adapter.submitData(it)
                    }
                } catch (e: Throwable) {
                    Timber.e("getFilmList $e")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isFilterChanged.collect {
                        if (it) adapter.refresh()
                    }
                }
            } catch (e: Throwable) {
                Timber.e("isFilterChanged $e")
            }
        }
    }
}