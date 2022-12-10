package com.example.feature_details.presentation.gallery.tabadapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.feature_details.presentation.gallery.gallery_in_category.GalleryFragment

class GalleryTabAdapter(
    fragment: Fragment,
    private val itemCount:Int,
    private val onCategoryClick: (position: Int) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = itemCount

    override fun createFragment(position: Int): Fragment {
        onCategoryClick(position)
        return GalleryFragment()
    }
}