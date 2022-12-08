package ru.zhdanon.skillcinema.data.filmgallery

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseFilmGallery(
    @Json(name = "items") val items: List<ItemImageGallery>,
    @Json(name = "total") val total: Int,
    @Json(name = "totalPages") val totalPages: Int
)