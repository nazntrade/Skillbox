package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.FragmentAllFilmsBinding
import com.becker.beckerSkillCinema.presentation.CinemaViewModel
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.allfilmadapter.AllFilmAdapter

class FragmentAllFilms :
    ViewBindingFragment<FragmentAllFilmsBinding>(FragmentAllFilmsBinding::inflate) {

    private val viewModel: CinemaViewModel by activityViewModels()
    private val incomeArgsCategory: FragmentAllFilmsArgs by navArgs()
    private lateinit var allFilmAdapter: AllFilmAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        setAdapter()
        setFilmList(incomeArgsCategory.currentCategory)
        doOnSwipe()
    }

    private fun setLayout() {
        binding.apply {
            allFilmsCategoryTv.text = incomeArgsCategory.currentCategory.text
            allFilmsToHomeBtn.setOnClickListener { requireActivity().onBackPressedDispatcher } //////////////
            progressGroupContainer
                .loadingRefreshBtn.setOnClickListener {
                    setFilmList(incomeArgsCategory.currentCategory)
                }
        }

        binding.allFilmsList.layoutManager =
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
    }

    private fun setAdapter() {

        allFilmAdapter = AllFilmAdapter { onClickFilm(it) }

        allFilmAdapter.addLoadStateListener { state -> // ???????
            val currentState = state.refresh
            binding.apply {
                allFilmsList.isVisible = currentState != LoadState.Loading
                progressGroupContainer.progressGroup.isVisible = currentState == LoadState.Loading
                progressGroupContainer.loadingRefreshBtn.isVisible =
                    currentState != LoadState.Loading
            }

            when (currentState) {
                is LoadState.Loading -> {
                    binding.apply {
                        allFilmsList.isVisible = false
                        progressGroupContainer.progressGroup.isVisible = true
                        progressGroupContainer.loadingRefreshBtn.isVisible = false
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        binding.allFilmsList.isVisible = true
                        progressGroupContainer.progressGroup.isVisible = false
                        progressGroupContainer.loadingRefreshBtn.isVisible = true
                    }
                }
                else -> {
                    binding.apply {
                        binding.allFilmsList.isVisible = false
                        progressGroupContainer.loadingProgressBar.isVisible = false
                        progressGroupContainer.loadingRefreshBtn.isVisible = true
                    }
                }
            }
        }
    }

    private fun setFilmList(currentCategory: CategoriesFilms) {
        if (incomeArgsCategory.currentCategory == CategoriesFilms.TV_SERIES) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.setAllSeries().collect {
                    allFilmAdapter.submitData(it)
                }
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.setAllFilmsByCategory(currentCategory)
                    .collect {
                        allFilmAdapter.submitData(it)
                    }
            }
        }
        binding.allFilmsList.adapter = allFilmAdapter
    }

    private fun onClickFilm(filmId: Int) {
//
//
    }

    private fun doOnSwipe() {
        val swiperefresh = binding.swiperefresh
        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = false
            setFilmList(incomeArgsCategory.currentCategory)
        }
    }
}