package com.example.hw17Lists2.artikles

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArticleAdapter(
    private val screens: List<ArticleScreen>,
    fragment: ViewPagerFragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen: ArticleScreen = screens[position]
        return ArticleFragment.newInstance(
            textRes = screen.textRes,
            drawableRes = screen.drawableRes
        )
    }
}