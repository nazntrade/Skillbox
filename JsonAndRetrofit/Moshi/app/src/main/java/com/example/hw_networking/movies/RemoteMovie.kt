package com.example.hw_networking.movies

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RemoteMovie(
    val imdbID: String,

    @Json(name = "Poster")
    val poster: String,

    @Json(name = "Title")
    val title: String,

    @Json(name = "Type")
    val type: String = "",

    @Json(name = "Year")
    val year: Int,
) : Parcelable
