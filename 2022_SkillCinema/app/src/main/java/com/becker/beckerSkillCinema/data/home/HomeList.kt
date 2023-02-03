package com.becker.beckerSkillCinema.data.home

import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.entity.HomeItem

data class HomeList(
    val category: CategoriesFilms,
    val filmList: List<HomeItem>
)