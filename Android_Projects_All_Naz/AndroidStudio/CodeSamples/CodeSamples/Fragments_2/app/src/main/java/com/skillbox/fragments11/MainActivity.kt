package com.skillbox.fragments11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.skillbox.fragment10.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val screens: List<OnboardingScreen> = listOf(
        OnboardingScreen(
            textRes = R.string.onboarding_text_1,
            bgColorRes = R.color.onboarding_color_1,
            drawableRes = R.drawable.onboarding_drawable_1
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_2,
            bgColorRes = R.color.onboarding_color_2,
            drawableRes = R.drawable.onboarding_drawable_2
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_3,
            bgColorRes = R.color.onboarding_color_3,
            drawableRes = R.drawable.onboarding_drawable_3
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_4,
            bgColorRes = R.color.onboarding_color_4,
            drawableRes = R.drawable.onboarding_drawable_4
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_5,
            bgColorRes = R.color.onboarding_color_5,
            drawableRes = R.drawable.onboarding_drawable_5
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = OnboardingAdapter(screens, this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        viewPager.setCurrentItem(2, false)
        viewPager.currentItem

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                toast("selected page = $position")
            }
        })

        viewPager.setPageTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                when {
                    position < -1 || position > 1 -> {
                        page.alpha = 0f
                    }

                    position <= 0 -> {
                        page.alpha = 1 + position
                    }

                    position <= 1 -> {
                        page.alpha = 1 - position
                    }
                }
            }
        })
    }
}
