package com.example.hw15_viewpager_dialog_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hw15_viewpager_dialog_fragments.databinding.FragmentViewpagerBinding
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import androidx.viewpager.widget.ViewPager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class ViewPagerFragment : Fragment(R.layout.fragment_viewpager) {

    val tags: List<ArticleTag> = listOf(
        ArticleTag.Rules,
        ArticleTag.Advices,
        ArticleTag.Health
    )

    private val screens: List<ArticleScreen> = listOf(
        ArticleScreen(
            textRes = R.string.s_o_l_i_d_,
//            tag = R.string.rules_tag,
            drawableRes = R.drawable.solid_img
        ),
        ArticleScreen(
            textRes = R.string.clean_code,
//            tag = R.string.rules_tag,
            drawableRes = R.drawable.cleane_code_img
        ),
        ArticleScreen(
            textRes = R.string.nineAdviseToDeveloper,
//            tag = R.string.advice_tag,
            drawableRes = R.drawable.android_professin_img
        ),
        ArticleScreen(
            textRes = R.string.typical_mistakes,
//            tag = R.string.advice_tag,
            drawableRes = R.drawable.android_img
        ),
        ArticleScreen(
            textRes = R.string.health_programmer,
//            tag = R.string.health_tag,
            drawableRes = R.drawable.sport_img
        )
    )

    private lateinit var binding: FragmentViewpagerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewpagerBinding.bind(view)

        val adapter = ArticleAdapter(screens, this)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 1
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)
        binding.viewPager.setPageTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                if (position < -1) {  // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.alpha = 0f
                } else if (position <= 1) { // [-1,1]
                    page.scaleX = Math.max(0.65f, 1 - Math.abs(position))
                    page.scaleY = Math.max(0.65f, 1 - Math.abs(position))
                    page.alpha = Math.max(0.3f, 1 - Math.abs(position))
                } else {  // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.alpha = 0f
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "S.O.L.I.D."
                1 -> tab.text = "Clean"
                2 -> tab.text = "Advise"
                3 -> tab.text = "Errors"
                4 -> tab.text = "Health"
            }
        }.attach()

        val len: Int = screens.size
        val randomGenerator = Random()
        val randomInt: Int = randomGenerator.nextInt(len)
        binding.generateButton.setOnClickListener {
            binding.tabLayout.getTabAt(randomInt)?.orCreateBadge?.apply {
                if (number == 0) {
                    number = 1
                } else number += 1
                badgeGravity = BadgeDrawable.TOP_END
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.removeBadge()
            }
        })
    }
}

