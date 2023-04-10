package com.becker.beckerSkillCinema.data.filmByFilter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "country") val country: String
)