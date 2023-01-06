package com.becker.beckerSkillCinema.data.filmGallery

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemImageGallery(
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "previewUrl") val previewUrl: String
)