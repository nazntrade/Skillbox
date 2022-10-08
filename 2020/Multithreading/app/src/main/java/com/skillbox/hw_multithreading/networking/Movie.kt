package com.skillbox.hw_multithreading.networking

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: Int,
    @SerializedName("Country")
    val country: String,
    @SerializedName("imdbRating")
    val imdbRating: String
)