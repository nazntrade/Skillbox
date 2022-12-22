package com.becker.beckerSkillCinema.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CategoriesFilms(val text: String) : Parcelable {
    BEST_250("ТОП-250"),
    POPULAR_100("Популярное"),
    PREMIERS("Премьеры"),
    MOST_AWAIT("Самые ожидаемые"),
    TV_SERIES("Сериалы"),
    BIOGRAPHY("Биографии"),
    SCIENCE_FICTION("Фантастика"),
    CARTOONS("Мультфильмы")
}