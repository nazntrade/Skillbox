package ru.zhdanon.skillcinema.data.filmspremier

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponsePremier(
    @Json(name = "items") val items: List<FilmPremier>,
    @Json(name = "total") val total: Int
)