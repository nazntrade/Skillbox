package com.example.lessons15_viewpager_and_dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lessons15_viewpager_and_dialogs.databinding.ActivityMainBinding
import com.example.lessons15_viewpager_and_dialogs.databinding.FragmentOnboardingBinding

class MainActivity : AppCompatActivity() {

//создадим список
    //создадим перем.она будет типа List<OnboardingScreen> и хранить данные кот.необх отобразить в списке
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

        private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = OnboardingAdapter(screens, this)
        binding.viewPager.adapter = adapter
    }
}