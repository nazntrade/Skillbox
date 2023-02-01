package com.becker.beckerSkillCinema.data.videoByFilmId

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseVideoByFilmId(
    @Json(name = "total") val total: Int,
    @Json(name = "items") val items: List<ItemVideoByFilmId>,
)
