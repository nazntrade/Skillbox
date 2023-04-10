package com.becker.beckerSkillCinema.data.filmsTop

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseTop(
    @Json(name = "films") val films: List<FilmTop>,
    @Json(name = "pagesCount") val pagesCount: Int
)