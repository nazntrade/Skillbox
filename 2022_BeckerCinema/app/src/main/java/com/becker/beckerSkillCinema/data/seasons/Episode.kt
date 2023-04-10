package com.becker.beckerSkillCinema.data.seasons

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    @Json(name = "seasonNumber") val seasonNumber: Int,
    @Json(name = "episodeNumber") val episodeNumber: Int,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "synopsis") val synopsis: String?,
    @Json(name = "releaseDate") val releaseDate: String?
)