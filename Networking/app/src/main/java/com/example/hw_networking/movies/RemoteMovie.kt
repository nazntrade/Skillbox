package com.example.hw_networking.movies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoteMovie(
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String,
) : Parcelable
