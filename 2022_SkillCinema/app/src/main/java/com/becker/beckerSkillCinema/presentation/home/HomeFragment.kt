package com.becker.beckerSkillCinema.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.FragmentHomeBinding
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.adapters.categoryAdapter.CategoryAdapter

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

//    https://slack-chats.kotlinlang.org/t/471784/can-anyone-explain-what-is-by-activityviewmodels-by-fragment
    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFilmsByCategories()
        stateLoadingListener()
        categoryObserve()
    }


    private fun categoryObserve() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homePageList.collect {
                categoryAdapter =
                    CategoryAdapter(20, it, { onClickShowAllButton(it) }, { onClickFilm(it) })
                binding.categoryList.adapter = categoryAdapter
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
//
//
    }

    private fun onClickShowAllButton(category: CategoriesFilms) {
//
//
    }

    private fun stateLoadingListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadCategoryState.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        binding.progressGroup.isVisible = true
                        binding.loadingProgress.isVisible = true
                        binding.loadingRefreshBtn.isVisible = false
                        binding.categoryList.isVisible = false
                    }
                    is StateLoading.Success -> {
                        binding.progressGroup.isVisible = false
                        binding.categoryList.isVisible = true
                    }
                    else -> {
                        binding.progressGroup.isVisible = true
                        binding.loadingProgress.isVisible = false
                        binding.loadingRefreshBtn.isVisible = true
                        binding.categoryList.isVisible = false
                        binding.loadingRefreshBtn.setOnClickListener { viewModel.getFilmsByCategories() }
                    }
                }
            }
        }
    }

}