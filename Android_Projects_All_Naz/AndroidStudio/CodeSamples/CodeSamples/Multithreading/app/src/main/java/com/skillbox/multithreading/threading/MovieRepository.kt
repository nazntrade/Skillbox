package com.skillbox.multithreading.threading

import android.util.Log
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import com.skillbox.multithreading.networking.Network.MOVIE_API_KEY
import java.util.Collections

class MovieRepository {

    fun getMovieById(movieId: String): Movie? {
        return Network.api().getMovieById(movieId, MOVIE_API_KEY).execute()
            .body() //ignore exceptions
    }

    fun fetchMovies(
        movieIds: List<String>,
        onMoviesFetched: (movies: String, fetchTime: Long) -> Unit
    ) {
        Log.d("ThreadTest", "fetchMovies start on ${Thread.currentThread().name}")
        Thread {

            val startTime = System.currentTimeMillis()
            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())

            val threads = movieIds.chunked(10).map { movieChunk ->
                Thread {
                    val movies = movieChunk.mapNotNull { movieId ->
                        getMovieById(movieId)
                    }
                    allMovies.addAll(movies)
                }
            }

            threads.forEach { it.start() }
            threads.forEach { it.join() }

            val requestTime = System.currentTimeMillis() - startTime

            val joinedMovies = allMovies.joinToString("\n")

            onMoviesFetched(joinedMovies, requestTime)
        }.start()
        Log.d("ThreadTest", "fetchMovies end on ${Thread.currentThread().name}")
    }
}