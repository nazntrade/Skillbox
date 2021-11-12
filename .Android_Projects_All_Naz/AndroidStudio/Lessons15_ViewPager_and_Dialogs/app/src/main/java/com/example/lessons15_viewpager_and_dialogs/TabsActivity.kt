package com.example.lessons15_viewpager_and_dialogs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.lessons15_viewpager_and_dialogs.databinding.ActivityTabsBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class   TabsActivity : AppCompatActivity() {

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
        ),
    )

    lateinit var binding: ActivityTabsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTabsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
                                         //сдел.вкладок в2а раза больше и они сжались
                                         //добавим ТабМод(скролабл) в разметку и все ОК
        val adapter = OnboardingAdapter(screens + screens, this)
        binding.viewPager.adapter = adapter

        //свяжем ТабЛайаут и ВюьПэйджер с помощью класса TabLayoutMediator
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
            //на каждую вторую вкладку установим иконку
            if (position % 2 == 0) {
                tab.setIcon(R.drawable.ic_android)
            }
        }.attach()

        // сделаем бейджик на 2ой вкладке, якобы на этом экране произ.2 нов.события
        binding.tabLayout.getTabAt(1)?.orCreateBadge?.apply {
            number = 2
            badgeGravity = BadgeDrawable.TOP_END
        }
        //и будем скрывать его,когда пользов.перейдет на тек.вкладку
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.removeBadge()
            }
        })
    }
}