package com.becker.beckerSkillCinema.data

import android.content.Context
import android.os.Parcelable
import com.becker.beckerSkillCinema.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CategoriesFilms(private val stringResId: Int) : Parcelable {
    BEST_250(R.string.category_best_250),
    POPULAR_100(R.string.popular_100),
    PREMIERS(R.string.premiers),
    MOST_AWAIT(R.string.most_await),
    TV_SERIES(R.string.tv_series),
    BIOGRAPHY(R.string.biography),
    SCIENCE_FICTION(R.string.science_fiction),
    CARTOONS(R.string.cartoons);

    @IgnoredOnParcel
    lateinit var text: String
        private set

    companion object {
        fun initialize(context: Context) {
            for (value in CategoriesFilms.values()) value.text =
                context.getString(value.stringResId)
        }
    }
}