package com.example.hw_networking.movies

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Scores(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
) : Parcelable
