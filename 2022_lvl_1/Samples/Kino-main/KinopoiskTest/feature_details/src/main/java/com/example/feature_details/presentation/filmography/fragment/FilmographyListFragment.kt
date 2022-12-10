package com.example.feature_details.presentation.filmography.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.extensions.popBackStack
import com.example.feature_details.R
import com.example.feature_details.databinding.FragmentTabGalleryOrFilmografyBinding
import com.example.feature_details.presentation.filmography.viewModel.FilmographyListViewModel
import com.example.feature_details.presentation.filmography.adapter.FilmographyTabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmographyListFragment : BaseFragment<FragmentTabGalleryOrFilmografyBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentTabGalleryOrFilmografyBinding.inflate(inflater)

    private val viewModel by viewModel<FilmographyListViewModel>()

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val category=  viewModel.divideByCategory()

        binding.categoryTitle.text = "Фильмография"

        binding.viewPagerGallery.adapter =  FilmographyTabAdapter(this, category.size){ position->
            viewModel.onCategoryClick(position)
        }

        TabLayoutMediator(binding.tabLayoutGallery,binding.viewPagerGallery){tab,position->

            binding.backArrow.popBackStack()

            val customTab =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_tab, null)

            val tabName = customTab.findViewById<TextView>(R.id.tab_name)
            val tabCounter = customTab.findViewById<TextView>(R.id.tab_image_counter)
            tabName.text = category[position].nameCategory
            tabCounter.text = category[position].items.size.toString()
            tab.customView = customTab

        }.attach()


    }
}