package com.becker.beckerSkillCinema.data

import com.becker.beckerSkillCinema.entity.HomeItem

data class HomeList(
    val category: CategoriesFilms,
    val filmList: List<HomeItem>
)