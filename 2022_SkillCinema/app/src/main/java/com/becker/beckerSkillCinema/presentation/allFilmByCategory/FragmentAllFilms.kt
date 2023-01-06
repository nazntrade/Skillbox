package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.FragmentAllFilmsBinding
import com.becker.beckerSkillCinema.presentation.MainViewModel
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.allFilmAdapters.AllFilmAdapter
import com.becker.beckerSkillCinema.utils.autoCleared

class FragmentAllFilms :
    ViewBindingFragment<FragmentAllFilmsBinding>(FragmentAllFilmsBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()
    private val incomeArgsCategory: FragmentAllFilmsArgs by navArgs()
    private var allFilmAdapter: AllFilmAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        setAdapter()
        setFilmList(incomeArgsCategory.currentCategory)
    }

    private fun setLayout() {
        binding.apply {
            allFilmsCategoryTv.text = incomeArgsCategory.currentCategory.text
            allFilmsToHomeBtn.setOnClickListener { findNavController().popBackStack() }
            progressGroupContainer
                .loadingRefreshBtn.setOnClickListener {
                    setFilmList(incomeArgsCategory.currentCategory)
                }
        }
        binding.allFilmsList.layoutManager =
            GridLayoutManager(
                requireContext(),
                3,
                GridLayoutManager.VERTICAL,
                false
            )
    }

    private fun setAdapter() {

        allFilmAdapter = AllFilmAdapter { onClickFilm(it) }

        allFilmAdapter.addLoadStateListener { state -> // ???????????????????????????????????????????
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
                        progressGroupContainer.noAnswerText.isVisible = false
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
                        progressGroupContainer.noAnswerText.isVisible = true
                        progressGroupContainer
                            .loadingRefreshBtn
                            .setOnClickListener { setFilmList(incomeArgsCategory.currentCategory) }
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
        val action = FragmentAllFilmsDirections.actionFragmentAllFilmsToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

}