package com.becker.beckerSkillCinema.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CategoriesFilms(val text: String) : Parcelable {
//    BIOGRAPHY("Биографии"),
//    SCIENCE_FICTION("Научная фантастика"),
    BEST_250("ТОП-250"),
    POPULAR_100("Популярное"),
    PREMIERS("Премьеры"),
    MOST_AWAIT("Самые ожиаемые"),
    TV_SERIES("Сериалы")
}