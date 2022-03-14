package com.skillbox.lessons_jsonandretrofit.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class MovieCustomAdapter {

    @FromJson
    fun fromJson(customMovie: CustomMovie): Movie {
        return Movie(
            id = customMovie.mainInfo.id,
            title = customMovie.mainInfo.title,
            year = customMovie.mainInfo.year,
            rating = customMovie.additionalInfo.rating,
            scores = customMovie.additionalInfo.scores
        )
    }

    @JsonClass(generateAdapter = true)
    data class MainInfoWrapper(
        @Json(name = "imdb_id")
        val id: String,
        val title: String,
        val year: Int
    )

    @JsonClass(generateAdapter = true)
    data class AdditionalInfoWrapper(
        val rating: MovieRating,
        val scores: List<Score>
    )

    @JsonClass(generateAdapter = true)
    data class CustomMovie(
        @Json(name = "main_info")
        val mainInfo: MainInfoWrapper,
        @Json(name = "additional_info")
        val additionalInfo: AdditionalInfoWrapper
    )
}