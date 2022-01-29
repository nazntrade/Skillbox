package com.skillbox.hw_multithreading.threading

import com.skillbox.hw_multithreading.networking.Movie
import com.skillbox.hw_multithreading.networking.Network
import java.io.IOException

class ThreadingRepository {

    val movieIds = listOf(
        "tt0268978",
        "tt3783958",
        "tt4513678",
        "tt4154796"
    )

    fun getMovieById(movieId: String): Movie? {
        return try {
            Network.api().getMovieById(movieId, Network.MOVIE_API_KEY).execute().body()
        } catch (e: IOException) {
            // Проблемы с интернет соединением
            null
        }
    }

    fun fetchMovie(
        movieIds: List<String>
    ) {

    }
}