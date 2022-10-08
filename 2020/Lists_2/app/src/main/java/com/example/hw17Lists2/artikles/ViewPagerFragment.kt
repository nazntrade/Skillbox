package com.example.hw17Lists2.artikles

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hw17Lists2.R
import com.example.hw17Lists2.artikles.ArticleTagEnum.Rules
import com.example.hw17Lists2.databinding.FragmentViewpagerBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.math.abs
import kotlin.math.max


class ViewPagerFragment : Fragment(R.layout.fragment_viewpager) {

    private var screens: List<ArticleScreen> = listOf(
        ArticleScreen(
            textRes = R.string.s_o_l_i_d_,
            textResHead = R.string.solid_head,
            tag = Rules,
            drawableRes = R.drawable.solid_img
        ),
        ArticleScreen(
            textRes = R.string.clean_code,
            textResHead = R.string.clean_head,
            tag = Rules,
            drawableRes = R.drawable.cleane_code_img
        ),
        ArticleScreen(
            textRes = R.string.nineAdviseToDeveloper,
            textResHead = R.string.advice_head,
            tag = ArticleTagEnum.Advise,
            drawableRes = R.drawable.android_professin_img
        ),
        ArticleScreen(
            textRes = R.string.typical_mistakes,
            textResHead = R.string.mistake_head,
            tag = ArticleTagEnum.Advise,
            drawableRes = R.drawable.android_img
        ),
        ArticleScreen(
            textRes = R.string.health_programmer,
            textResHead = R.string.health_head,
            tag = ArticleTagEnum.Health,
            drawableRes = R.drawable.sport_img
        )
    )

    private lateinit var binding: FragmentViewpagerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewpagerBinding.bind(view)

        adapter(screens)

        binding.viewPager.offscreenPageLimit = 1
        wormDotsIndicator()
        binding.viewPager.setPageTransformer { page, position ->
            when {
                position < -1 -> {  // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    page.scaleX = max(0.65f, 1 - abs(position))
                    page.scaleY = max(0.65f, 1 - abs(position))
                    page.alpha = max(0.3f, 1 - abs(position))
                }
                else -> {  // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.alpha = 0f
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(screens[position].textResHead)
        }.attach()

        val len: Int = screens.size
        randomGenerator(len)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.removeBadge()
            }
        })

        //DialogWindow filter
        binding.bottomFilter.setOnClickListener {
            showDialogWithSingleChoice()
        }
    }

    private var checkedItems = BooleanArray(ArticleTagEnum.values().size) { true }

    private fun showDialogWithSingleChoice() {
        val tags = ArticleTagEnum.values().map { it.name }.toTypedArray()
        val filterArticles: MutableList<String> = mutableListOf()
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_window_kitten))
            .setMultiChoiceItems(tags, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton("Ok") { _, _ ->
                for (i in tags.indices) {
                    val checked = checkedItems[i]
                    if (checked) {
                        filterArticles.add(tags[i])
                    }
                }
                val newScreens: List<ArticleScreen> =
                    screens.filter { it.tag.name in filterArticles }

                adapter(newScreens)

                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.setText(newScreens[position].textResHead)
                }.attach()

                wormDotsIndicator()

                val len: Int = newScreens.size
                randomGenerator(len)

            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun randomGenerator(len: Int) {
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
    }

    private fun wormDotsIndicator() {
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)
    }


    private fun adapter(objects: List<ArticleScreen>) {
        val adapter = ArticleAdapter(objects, this)
        binding.viewPager.adapter = adapter
    }
}
