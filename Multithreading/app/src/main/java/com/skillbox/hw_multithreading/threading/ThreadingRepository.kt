package com.skillbox.hw_multithreading.threading

import android.util.Log
import com.skillbox.hw_multithreading.networking.Movie
import com.skillbox.hw_multithreading.networking.Network
import com.skillbox.hw_multithreading.networking.Network.MOVIE_API_KEY
import java.util.*

class ThreadingRepository {

    val movieIds = listOf(
        "tt0268978",
        "tt3783958",
        "tt4513678",
        "tt4154796",
        "tt0090605",
        "tt0338013",
        "tt0017136",
        "tt8579674",
        "tt0095016",
        "tt0084787",
        "tt0015864",
        "tt0107290",
        "tt1160419",
        "tt2024544",
        "tt0108052",
        "tt0110912",
        "tt0109830",
        "tt1375666",
        "tt0073486",
        "tt0076759",
        "tt0253474"
    )

    val movieFromRepository
        get() = newMovie

    private var newMovie: MutableList<Movie> = Collections.synchronizedList(mutableListOf<Movie>())

    private fun getMovieById(movieId: String): Movie? {
        return Network.api().getMovieById(movieId, MOVIE_API_KEY).execute()
            .body()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    fun fetchMovie(
        movieIds: List<String>,
        onMoviesFetched: (movies: List<Movie>) -> Unit
    ) {
        newMovie = emptyList<Movie>().toMutableList()
        Thread {
            Log.d("ThreadTest", "livedata changed on ${Thread.currentThread().name}")

            val threads = movieIds.chunked(1).map { movieChunk ->
                Thread {
                    val movies = movieChunk.mapNotNull { movieId ->
                        getMovieById(movieId)
                    }
                    newMovie.addAll(movies)
                }
            }

            threads.forEach { it.start() }
            threads.forEach { it.join() }

            onMoviesFetched(newMovie)
        }.start()
    }
}