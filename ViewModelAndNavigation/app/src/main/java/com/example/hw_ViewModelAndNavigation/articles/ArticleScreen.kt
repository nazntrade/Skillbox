package com.example.hw_ViewModelAndNavigation.articles

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticleScreen(
    @StringRes val textRes: Int,
    @StringRes val textResHead: Int,
    @DrawableRes val drawableRes: Int,
    val tag: ArticleTagEnum
)
