package com.example.hw15_viewpager_dialog_fragments

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ArticleTag(
    @StringRes val nameId: Int
) : Parcelable {
    RULES("Rules"),
    ADVISE("Advise"),
    HEALTH("Health")
}