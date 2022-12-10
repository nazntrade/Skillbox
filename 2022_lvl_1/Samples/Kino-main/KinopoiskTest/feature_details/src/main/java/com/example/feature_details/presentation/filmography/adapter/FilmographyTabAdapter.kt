package com.example.feature_details.presentation.filmography.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.feature_details.presentation.filmography.fragment.FilmographyFragment

class FilmographyTabAdapter(
    fragment: Fragment,
    private val itemCount:Int,
    private val onCategoryClick: (position:Int)->Unit
) : FragmentStateAdapter(fragment) {


    override fun getItemCount() = itemCount

    override fun createFragment(position: Int): Fragment {
        onCategoryClick(position)
        return FilmographyFragment()
    }
}