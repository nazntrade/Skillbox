package com.becker.beckerSkillCinema.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.becker.beckerSkillCinema.databinding.FragmentHomeBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stateLoadingListener()
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
            binding.loadingRefreshBtn.setOnClickListener {/* viewModel.getFilmsByCategories()*/ }
                    }
                }
            }
        }
    }

}