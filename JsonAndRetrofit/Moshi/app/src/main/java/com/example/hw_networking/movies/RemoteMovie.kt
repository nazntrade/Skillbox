package com.example.hw_networking.movies

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RemoteMovie(

    @Json(name = "imdbID")
    val imdbID: String,

    @Json(name = "Poster")
    val poster: String,

    @Json(name = "Title")
    val title: String,

    @Json(name = "Type")
    val type: String = "",

    @Json(name = "Year")
    val year: String,

    @Json(name = "Plot")
    val plot: String,

    @Json(name = "Genre")
    val genre: String,

    @Json(name = "Ratings")
    val rating: List<String>

) : Parcelable
