package com.example.hw_networking.adapter

import com.example.hw_networking.movies.MovieAndTvRatings
import com.example.hw_networking.movies.RemoteMovie
import com.example.hw_networking.movies.Scores
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class JsonCustomAdapter {
    @JsonClass(generateAdapter = true)
    data class CustomRemoteMovie(
        @Json(name = "imdbID")
        val imdbID: String,
        @Json(name = "Poster")
        val poster: String,
        @Json(name = "Title")
        val title: String,
        @Json(name = "Type")
        val type: String,
        @Json(name = "Year")
        val year: String,
        @Json(name = "Plot")
        val plot: String,
        @Json(name = "Genre")
        val genre: String,
        @Json(name = "Rated")
        val rated: MovieAndTvRatings,
        @Json(name = "Ratings")
        val ratings: List<Scores>
    )

//    ratings = movieRatings.ratings.map { rating ->
//        rating.source to rating.value
//    }.toMap()

    @FromJson
    fun fromJson(customRemoteMovie: CustomRemoteMovie): RemoteMovie {
        return RemoteMovie(
            imdbID = customRemoteMovie.imdbID,
            poster = customRemoteMovie.poster,
            title = customRemoteMovie.title,
            year = customRemoteMovie.year,
            type = customRemoteMovie.type,
            plot = customRemoteMovie.plot,
            genre = customRemoteMovie.genre,
            rated = customRemoteMovie.rated,
            ratings = customRemoteMovie.ratings.associate { rating -> ////WTF?
                rating.source to rating.value
            }
        )
    }
}