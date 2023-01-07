package com.becker.beckerSkillCinema.data

object DataCentre {

    private var currentCategory: CategoriesFilms? = null

    fun putCategory(it: CategoriesFilms) {
        currentCategory = it
    }

    fun getCurrentCategory(): CategoriesFilms? {
        val it = currentCategory
        currentCategory = null
        return it
    }

}