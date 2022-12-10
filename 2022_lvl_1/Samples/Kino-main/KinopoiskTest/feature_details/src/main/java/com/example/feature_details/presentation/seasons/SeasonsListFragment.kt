package com.example.feature_details.presentation.seasons

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.base_model.category.season.Season
import com.example.core.tools.extensions.popBackStack
import com.example.feature_details.R
import com.example.feature_details.databinding.FragmentTabGalleryOrFilmografyBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeasonsListFragment: BaseFragment<FragmentTabGalleryOrFilmografyBinding>() {
    override fun initBinding(inflater: LayoutInflater) =
        FragmentTabGalleryOrFilmografyBinding.inflate(inflater)

    private val viewModel by viewModel<SeasonsListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.backArrow.popBackStack()

        binding.season.isVisible = true

        binding.viewPagerGallery.adapter =
            SeasonsAdapter(this,viewModel.getListInfo().size){viewModel.createTabInfo(it)}

        setTabLayout(viewModel.getListInfo())
    }

    @SuppressLint("InflateParams")
    private fun setTabLayout(list: List<Season>) {
        TabLayoutMediator(
            binding.tabLayoutGallery,
            binding.viewPagerGallery
        ) { tab, position ->

            val customTab =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_seasone, null)

            val tabName = customTab.findViewById<TextView>(R.id.tab_name)
            tabName.text = list[position].number.toString()
            tab.customView = customTab
        }.attach()
    }

}