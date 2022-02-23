package com.example.hw_networking.movies

data class RemoteMovie(
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String,
    val country: String,
    val runtime: String,
    val plot: String
)
