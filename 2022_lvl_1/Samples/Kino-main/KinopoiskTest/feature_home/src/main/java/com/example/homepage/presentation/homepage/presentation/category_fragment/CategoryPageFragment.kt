package com.example.homepage.presentation.homepage.presentation.category_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.core.R
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.NAVIGATE__TO_INFO_FILM
import com.example.core.tools.NAVIGATE__TO_STAFF
import com.example.core.tools.adapter.adapters.OneCategoryForPaging
import com.example.core.tools.base_model.category.StartCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.createErrorDialog
import com.example.core.tools.extensions.popBackStack
import com.example.homepage.databinding.FragmentCategoryPageBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryPageFragment : BaseFragment<FragmentCategoryPageBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentCategoryPageBinding.inflate(inflater)

    private val viewModel by viewModel<CategoryPageViewModel>()

    private val adapter by lazy { OneCategoryForPaging { onClickItem(it) } }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryPageRecyclerView.adapter = adapter

        binding.categoryTitle.text = viewModel.getNameCategory()

        setAdapter()

        loadStateListener()

        binding.backArrow.popBackStack()

    }

    private fun onClickItem(pageCategory: StartCategory) {
        when (pageCategory.category){
            CategoryInfo.STAFF -> {
                viewModel.navigateTo(NAVIGATE__TO_STAFF, pageCategory.query?.id)
                findNavController().navigate(R.id.action_categoryPageFragment_to_staffInfoFragment)
            }
            else->{
                viewModel.navigateTo(NAVIGATE__TO_INFO_FILM,pageCategory.query?.id)
                findNavController().navigate(R.id.action_categoryPageFragment_to_filmInfoFragment)
            }
        }

    }

    private fun loadStateListener() {
        adapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
            if (it.source.refresh is LoadState.Error){ createErrorDialog() }
        }
    }

    private fun setAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFilms().collect {
                adapter.submitData(it)
            }
        }
    }
}