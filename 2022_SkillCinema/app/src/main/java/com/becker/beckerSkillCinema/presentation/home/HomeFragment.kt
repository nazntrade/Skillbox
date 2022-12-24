package com.becker.beckerSkillCinema.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.FragmentHomeBinding
import com.becker.beckerSkillCinema.presentation.CinemaViewModel
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.adapters.categoryAdapter.CategoryAdapter

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    // https://slack-chats.kotlinlang.org/t/471784/can-anyone-explain-what-is-by-activityviewmodels-by-fragment
    private val viewModel: CinemaViewModel by activityViewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFunction()
        doOnSwipe()
    }

    private fun mainFunction() {
        viewModel.getFilmsByCategories()
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

    private fun onClickShowAllButton(category: CategoriesFilms) {
        viewModel.setCurrentCategory(category)
        findNavController().navigate(R.id.action_fragmentHome_to_fragmentAllFilms)
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
            mainFunction()
        }
    }
}