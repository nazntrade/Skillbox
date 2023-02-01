package com.becker.beckerSkillCinema.data.videoByFilmId

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemVideoByFilmId(
    @Json(name = "url") val url: String,
    @Json(name = "name") val name: String,
    @Json(name = "site") val site: String
)
