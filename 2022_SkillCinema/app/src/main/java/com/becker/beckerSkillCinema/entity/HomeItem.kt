package com.becker.beckerSkillCinema.entity

import com.becker.beckerSkillCinema.data.Genre

interface HomeItem {
    val filmId: Int
    val posterUrlPreview: String
    val nameRu: String?
    val rating: String?
    val genres: List<Genre>
}