package com.example.presentation_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.NAVIGATE__CATEGORY_PAGE
import com.example.core.tools.adapter.adapters.CategoryAdapterBase
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.createAddDialog
import com.example.presentation_profile.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun initBinding(inflater: LayoutInflater) = FragmentProfileBinding.inflate(inflater)

    private val viewModel by viewModel<ProfileViewModel>()

   private val adapter = CategoryAdapterBase({onClickItem(it)}){ onClickAll(it)}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCollection()
        observe()

    }

    private fun onClickAll(category: BaseCategory){
        viewModel.navigateToCategory(category, NAVIGATE__CATEGORY_PAGE)
            findNavController().navigate(com.example.core.R.id.action_profileFragment_to_categoryPageFragment)
    }

    private fun onClickItem(pageCategory: PageCategory){
        if (pageCategory.category == CategoryInfo.COLLECTION) {
            if (pageCategory.query?.id == null) createAddDialog { viewModel.addCollection(it) }
            else {
                viewModel.navigateToCategory(pageCategory, NAVIGATE__CATEGORY_PAGE)
                findNavController().navigate(com.example.core.R.id.action_profileFragment_to_categoryPageFragment)
            }
        } else {
            viewModel.navigateToInfoFilms(pageCategory.query?.id)
            findNavController().navigate(com.example.core.R.id.action_profileFragment_to_filmInfoFragment)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profile.collect {
                adapter.items = it
                binding.recyclerView.adapter = adapter
            }
        }
    }
}