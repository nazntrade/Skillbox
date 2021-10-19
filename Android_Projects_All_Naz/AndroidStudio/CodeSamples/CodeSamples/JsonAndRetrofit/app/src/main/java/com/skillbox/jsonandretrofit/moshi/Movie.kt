package com.skillbox.jsonandretrofit.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "imdb_id")
    val id: String,
    val title: String,
    val year: Int,
    val rating: MovieRating = MovieRating.GENERAL,
    val scores: List<Score> = emptyList()
)