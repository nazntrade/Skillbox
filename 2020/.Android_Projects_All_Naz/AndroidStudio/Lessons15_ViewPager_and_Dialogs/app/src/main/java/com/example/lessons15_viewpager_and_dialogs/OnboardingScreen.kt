package com.example.lessons15_viewpager_and_dialogs

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

//для хранения ресурсов
//будет хранить те же параметры, кот.необх.передавать во фрагмент OnboardingFragment
data class OnboardingScreen(
    @StringRes val textRes: Int,
    @ColorRes val bgColorRes: Int,
    @DrawableRes val drawableRes: Int
)
