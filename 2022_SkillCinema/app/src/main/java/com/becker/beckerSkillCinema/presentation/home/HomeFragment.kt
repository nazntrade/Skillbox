package com.becker.beckerSkillCinema.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.FragmentHomeBinding
import com.becker.beckerSkillCinema.presentation.MainViewModel
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.adapters.categoryAdapter.CategoryAdapter
import com.becker.beckerSkillCinema.utils.autoCleared

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    // https://slack-chats.kotlinlang.org/t/471784/can-anyone-explain-what-is-by-activityviewmodels-by-fragment
    private val viewModel: MainViewModel by activityViewModels()

    private var categoryAdapter: CategoryAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFunctions()
        doOnSwipe()
    }

    private fun mainFunctions() {
        stateLoadingListener()
        categoryObserve()
    }

    private fun categoryObserve() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homePageFilmList.collect { homeLists ->
                categoryAdapter =
                    CategoryAdapter(
                        20,
                        homeLists,
                        { categoriesFilms -> onClickShowAllButton(categoriesFilms) },
                        { onClickFilm(it) })

                binding.categoryList.adapter = categoryAdapter
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
//
//
    }

    private fun onClickShowAllButton(currentCategory: CategoriesFilms) {
        val direction =
            HomeFragmentDirections.actionFragmentHomeToFragmentAllFilms(currentCategory)
        findNavController().navigate(direction)
    }

    private fun stateLoadingListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadCategoryState.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        binding.apply {
                            progressGroupContainer.progressGroup.isVisible = true
                            progressGroupContainer.loadingProgressBar.isVisible = true
                            progressGroupContainer.loadingRefreshBtn.isVisible = false
                            progressGroupContainer.noAnswerText.isVisible = false

                            categoryList.isVisible = false
                        }
                    }
                    is StateLoading.Success -> {
                        binding.apply {
                            progressGroupContainer.progressGroup.isVisible = false
                            categoryList.isVisible = true
                        }
                    }
                    else -> {
                        binding.apply {
                            progressGroupContainer.progressGroup.isVisible = true
                            progressGroupContainer.loadingProgressBar.isVisible = false
                            progressGroupContainer.loadingRefreshBtn.isVisible = true
                            progressGroupContainer.noAnswerText.isVisible = true
                            categoryList.isVisible = false
                            progressGroupContainer
                                .loadingRefreshBtn
                                .setOnClickListener { viewModel.getFilmsByCategories() }
                        }
                    }
                }
            }
        }
    }


    private fun doOnSwipe() {
        val swiperefresh = binding.swiperefresh
        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = false
            mainFunctions()
        }
    }
}