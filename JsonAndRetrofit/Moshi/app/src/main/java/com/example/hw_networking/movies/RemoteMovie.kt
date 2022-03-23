package com.example.hw_networking.movies

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RemoteMovie(
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String,
    val plot: String,
    val genre: String,
    val rated: MovieAndTvRatings,
    var ratings: Map<String, String>
) : Parcelable
