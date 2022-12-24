package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            allFilmsCategoryTv.text = viewModel.getCurrentCategory().text
            allFilmsToHomeBtn.setOnClickListener { requireActivity().onBackPressedDispatcher }
            progressGroupContainer
                .loadingRefreshBtn.setOnClickListener { viewModel.getAllFilmAdapter().retry() }
        }

        setAdapter()                // Установка адаптера
        setFilmList()               // Установка списка фильмов
        doOnSwipe()
    }

    private fun setAdapter() {
        viewModel.setAllFilmAdapter(AllFilmAdapter { onClickFilm(it) })

        binding.allFilmsList.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.allFilmsList.adapter = viewModel.getAllFilmAdapter()

        viewModel.getAllFilmAdapter().addLoadStateListener { state ->
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

    private fun setFilmList() {
        if (viewModel.getCurrentCategory() == CategoriesFilms.TV_SERIES) {
//            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//                viewModel.allSeries.collect {
//                    viewModel.getAllFilmAdapter().submitData(it)
//                }
//            }
        } else {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.allFilmsByCategory.collect {
                    viewModel.getAllFilmAdapter().submitData(it)
                }
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
//
//
    }

    private fun doOnSwipe() {
//        val swiperefresh = binding.swiperefresh
//        swiperefresh.setOnRefreshListener {
//            swiperefresh.isRefreshing = false
//            mainFunction()
//        }
    }

}