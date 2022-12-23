package com.becker.beckerSkillCinema.data.filmbyfilter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseByFilter(
    @Json(name = "items") val items: List<FilmByFilter>,
    @Json(name = "total") val total: Int,
    @Json(name = "totalPages") val totalPages: Int
)