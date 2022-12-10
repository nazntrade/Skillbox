package com.example.feature_details.presentation.seasons

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SeasonsAdapter(
    fragment: Fragment,
    private val itemCount:Int,
    private val onCategoryClick: (position: Int) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = itemCount

    override fun createFragment(position: Int): Fragment {
        onCategoryClick(position)
        return SeasonsFragment()
    }
}