package com.becker.beckerSkillCinema.data

object DataCentre {

    private var currentCategory: CategoriesFilms? = null

    private var currentFilmId: Int? = null

    fun putCurrentFilmId(it: Int) {
        currentFilmId = it
    }

    fun getCurrentFilmId(): Int? {
        val it = currentFilmId
        currentFilmId = null
        return it
    }

    fun putCategory(it: CategoriesFilms) {
        currentCategory = it
    }

    fun getCurrentCategory(): CategoriesFilms? {
        val it = currentCategory
        currentCategory = null
        return it
    }
}