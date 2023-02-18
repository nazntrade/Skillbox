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
import com.becker.beckerSkillCinema.presentation.search.adapters.SearchPeopleAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()
    private var adapterFilms: SearchAdapter by autoCleared()
    private var adapterPeople: SearchPeopleAdapter by autoCleared()
    private var isEditFocused = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setSearchString()
        setSearchType()
        getFilmList()
    }

    private fun setSearchType() {
        when (viewModel.getSearchType()) {
            "films" -> binding.searchRadioFilms.isChecked = true
            "people" -> binding.searchRadioPeople.isChecked = true
        }

        binding.searchTypeRadioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.search_radio_films -> viewModel.updateSearchType(newType = "films")
                R.id.search_radio_people -> viewModel.updateSearchType(newType = "people")
            }
        }
    }

    private fun setAdapter() {
        adapterFilms = SearchAdapter { onFilmClick(it) }

        binding.searchFilmList.layoutManager =
            GridLayoutManager(
                requireContext(),
                1,
                GridLayoutManager.VERTICAL,
                false
            )

        binding.searchFilmList.adapter = adapterFilms

        adapterFilms.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.searchFilmList.smoothScrollToPosition(0)
                }
            }
        })

        adapterFilms.addLoadStateListener { state ->
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
                if (viewModel.getSearchType() == "films") {
                    findNavController().navigate(R.id.action_fragmentSearch_to_searchSettingsFragment)
                }
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
                        delay(3000)
                        if (s.toString() != viewModel.getFilters().keyword) {
                            viewModel.updateFilters(
                                filterFilm = viewModel.getFilters().copy(keyword = s.toString())
                            )
                            viewModel.getPeople(s.toString())
                            if (viewModel.getSearchType() == "films") adapterFilms.refresh()
                            if (viewModel.getSearchType() == "people") adapterPeople.refresh()

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
                    if (viewModel.getSearchType() == "films") {
                        viewModel.pagedFilms?.collect {
                            adapterFilms.submitData(it)
                        }
                    }
                    if (viewModel.getSearchType() == "people") {
                        viewModel.peopleFromSearch.collect {
//                            adapterPeople.submitData(it)///////////////////////
                        }
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
                        if (viewModel.getSearchType() == "films") {
                            if (it) adapterFilms.refresh()
                        }
                    }
                }
            } catch (e: Throwable) {
                Timber.e("isFilterChanged $e")
            }
        }
    }
}