package com.example.hw15_viewpager_dialog_fragments

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticleScreen(
    @StringRes val textRes: Int,
    @DrawableRes val drawableRes: Int,
    val tag: ArticleTagEnum
)
