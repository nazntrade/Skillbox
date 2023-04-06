package com.becker.beckerSkillCinema.data

import android.os.Parcelable
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.utils.MyStrings
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CategoriesFilms(val text: String) : Parcelable {
    BEST_250(text = MyStrings.get(R.string.category_best_250)),
    POPULAR_100(text = MyStrings.get(R.string.popular_100)),
    PREMIERS(text = MyStrings.get(R.string.premiers)),
    MOST_AWAIT(text = MyStrings.get(R.string.most_await)),
    TV_SERIES(text = MyStrings.get(R.string.tv_series)),
    BIOGRAPHY(text = MyStrings.get(R.string.biography)),
    SCIENCE_FICTION(text = MyStrings.get(R.string.science_fiction)),
    CARTOONS(text = MyStrings.get(R.string.cartoons));
}