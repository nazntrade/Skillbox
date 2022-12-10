package com.example.homepage.presentation.homepage.presentation.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.R
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.adapter.adapters.CategoryAdapterBase
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.StartCategory
import com.example.core.tools.extensions.checkFirstStart
import com.example.core.tools.extensions.observeLoadState
import com.example.homepage.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initBinding(inflater: LayoutInflater) = FragmentHomeBinding.inflate(inflater)

    private val viewModel by viewModel<HomeViewModel>()

    private val adapter by lazy { CategoryAdapterBase({onClickFilms(it)},{onClickNextButton(it)})  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoadState(viewModel.loadState, binding.progressBar) {}

        createAdapter()

        onFirstStart()

    }

    private fun onFirstStart(){
        if (checkFirstStart()) {
            viewModel.createStartCollection()
            findNavController().navigate(R.id.action_homeFragment_to_onBoardingFragment)
        }
    }

    private fun createAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homePageList.collect { premiers ->
                binding.categoryRecyclerView.adapter =
                    adapter.apply {
                        items =premiers
                    }
            }
        }
    }

    private fun onClickNextButton(categoryFilms: BaseCategory) {
        viewModel.navigateToCategory(categoryFilms)
        findNavController().navigate(R.id.action_homeFragment_to_categoryPageFragment)
    }

    private fun onClickFilms(pageCategory: StartCategory) {
        viewModel.navigateToInfoFilm(pageCategory.query?.id)
        findNavController().navigate(R.id.action_homeFragment_to_filmInfoFragment)
    }
}