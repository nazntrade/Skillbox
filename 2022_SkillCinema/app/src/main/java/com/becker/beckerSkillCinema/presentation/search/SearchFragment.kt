package com.becker.beckerSkillCinema.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
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
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.search.adapters.SearchAdapter
import com.becker.beckerSkillCinema.presentation.search.adapters.SearchPeopleAdapter
import com.becker.beckerSkillCinema.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var adapterFilms: SearchAdapter
    private lateinit var adapterPeople: SearchPeopleAdapter
    private var isEditFocused = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()
        setSearchType()
        setSearchString()
        getFilmList()
        getPeopleList()
        stateLoadingListener()
    }

    private fun setSearchType() {
        when (viewModel.getSearchType()) {
            Constants.TYPE_FILM -> {
                binding.searchRadioFilms.isChecked = true
                toggleList()
            }
            Constants.TYPE_PEOPLE -> {
                binding.searchRadioPeople.isChecked = true
                toggleList()
            }
        }

        binding.searchTypeRadioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.search_radio_films -> {
                    viewModel.updateSearchType(newType = Constants.TYPE_FILM)
                    toggleList()
                }
                R.id.search_radio_people -> {
                    viewModel.updateSearchType(newType = Constants.TYPE_PEOPLE)
                    toggleList()
                }
            }
        }
    }

    private fun toggleList() {
        if (viewModel.getSearchType() == Constants.TYPE_FILM) {
            binding.searchFilmList.isVisible = true
            binding.searchPeopleList.isInvisible = true
            viewModel.getFilms()
            if (viewModel.getFilters().keyword != "") {
                adapterFilms.refresh()
            }
        }
        if (viewModel.getSearchType() == Constants.TYPE_PEOPLE) {
            binding.searchPeopleList.isVisible = true
            binding.searchFilmList.isInvisible = true
            if (viewModel.getFilters().keyword != "") {
                viewModel.getPeople()
            }
        }
    }

    private fun setAdapters() {
        adapterFilms = SearchAdapter { onFilmClick(it) }
        adapterPeople = SearchPeopleAdapter { onPeopleClick(it) }

        listOf(binding.searchFilmList, binding.searchPeopleList).forEach {
            it.layoutManager =
                GridLayoutManager(
                    requireContext(),
                    1,
                    GridLayoutManager.VERTICAL,
                    false
                )
        }
        binding.searchFilmList.adapter = adapterFilms
        binding.searchPeopleList.adapter = adapterPeople

        listOf(adapterFilms, adapterPeople).forEach {
            it.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0) {
                        binding.searchFilmList.smoothScrollToPosition(0)
                    }
                }
            })
        }
    }

    private fun stateLoadingListener() {
        //LOADING STATE FILM
        adapterFilms.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Loading -> {
                    binding.apply {
                        searchFilmList.isVisible = false
                        searchProgressText.isVisible = false
                        if (viewModel.getSearchType() == Constants.TYPE_FILM) {
                            searchProgressGroup.isVisible = true
                            loadingProgress.isVisible = true
                            searchProgressImage.isVisible = true
                        }
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        if (viewModel.getSearchType() == Constants.TYPE_FILM) {
                            searchFilmList.isVisible = true
                            searchPeopleList.isVisible = false
                        }
                        loadingProgress.isVisible = false
                        searchProgressText.isVisible = false
                        searchProgressImage.isVisible = false
                    }
                }
                else -> {
                    binding.apply {
                        searchFilmList.isVisible = false
                        searchPeopleList.isVisible = false
                        loadingProgress.isVisible = false
                        searchProgressText.isVisible = true
                        searchProgressImage.isVisible = true
                    }
                }
            }
        }
        //LOADING STATE PEOPLE
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadingState.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        binding.apply {
                            searchFilmList.isVisible = false
                            searchPeopleList.isVisible = false
                            searchProgressGroup.isVisible = true
                            loadingProgress.isVisible = true
                            searchProgressText.isVisible = false
                            searchProgressImage.isVisible = true
                        }
                    }
                    is StateLoading.Success -> {
                        binding.apply {
                            if (viewModel.getSearchType() == Constants.TYPE_PEOPLE) {
                                searchPeopleList.isVisible = true
                                searchFilmList.isVisible = false
                            }
                            loadingProgress.isVisible = false
                            searchProgressText.isVisible = false
                            searchProgressImage.isVisible = false
                        }
                    }
                    else -> {
                        binding.apply {
                            searchFilmList.isVisible = false
                            searchPeopleList.isVisible = false
                            loadingProgress.isVisible = false
                            searchProgressImage.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun onPeopleClick(peopleId: Int) {
        val action =
            SearchFragmentDirections.actionFragmentSearchToFragmentFilmDetail(peopleId)
        findNavController().navigate(action)
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
                if (viewModel.getSearchType() == Constants.TYPE_FILM) {
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
                        delay(2000)
                        if (viewModel.getSearchType() == Constants.TYPE_FILM) {
                            if (s.toString() != viewModel.getFilters().keyword) {
                                viewModel.updateFilters(
                                    filterFilm = viewModel.getFilters().copy(keyword = s.toString())
                                )
                                adapterFilms.refresh()
                            }
                        }
                        if (viewModel.getSearchType() == Constants.TYPE_PEOPLE) {
                            if (s.toString() != viewModel.getFilters().keyword) {
                                viewModel.updateFilters(
                                    filterFilm = viewModel.getFilters().copy(keyword = s.toString())
                                )
                                viewModel.getPeople()
                            }
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.pagedFilms?.collect {
                        adapterFilms.submitData(it)
                    }
                } catch (e: Throwable) {
                    Timber.e("getFilmList $e")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isFilterChanged.collect {
                        if (viewModel.getSearchType() == Constants.TYPE_FILM) {
                            if (it) adapterFilms.refresh()
                        }
                    }
                }
            } catch (e: Throwable) {
                Timber.e("isFilterChanged $e")
            }
        }
    }

    private fun getPeopleList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.peopleFromSearch.collect {
                        adapterPeople.submitList(it)
                    }
                } catch (e: Throwable) {
                    Timber.e("getPeopleList $e")
                }
            }
        }
    }
}