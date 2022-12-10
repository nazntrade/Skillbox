package com.example.feature_details.presentation.gallery.allgallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.extensions.observeLoadState
import com.example.feature_details.R
import com.example.feature_details.data.ImageCategory
import com.example.feature_details.databinding.FragmentTabGalleryOrFilmografyBinding
import com.example.feature_details.presentation.gallery.tabadapter.GalleryTabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListGalleryFragment : BaseFragment<FragmentTabGalleryOrFilmografyBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentTabGalleryOrFilmografyBinding.inflate(inflater)

    private val viewModel by viewModel<ListGalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGallery()

        observeLoadState(viewModel.loadState,binding.progressBar){}

        observe()

    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { categoryList ->
                binding.viewPagerGallery.adapter =
                    GalleryTabAdapter(this@ListGalleryFragment, categoryList.size) {viewModel.onClickCategory(it)}
                setTabLayout(categoryList)

            }
        }
    }


    @SuppressLint("InflateParams")
    private fun setTabLayout(categoryList: List<ImageCategory>) {
        TabLayoutMediator(
            binding.tabLayoutGallery,
            binding.viewPagerGallery
        ) { tab, position ->

            val customTab =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_tab, null)

            val tabName = customTab.findViewById<TextView>(R.id.tab_name)
            val tabCounter = customTab.findViewById<TextView>(R.id.tab_image_counter)
            tabName.text = categoryList[position].nameCategory
            tabCounter.text = categoryList[position].total.toString()
            tab.customView = customTab
        }.attach()
    }

}