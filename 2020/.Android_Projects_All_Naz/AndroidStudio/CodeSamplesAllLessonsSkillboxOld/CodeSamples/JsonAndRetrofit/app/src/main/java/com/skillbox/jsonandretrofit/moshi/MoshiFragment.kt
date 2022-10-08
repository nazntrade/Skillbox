package com.skillbox.jsonandretrofit.moshi

import androidx.fragment.app.Fragment
import com.skillbox.jsonandretrofit.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.fragment_moshi.*

class MoshiFragment : Fragment(R.layout.fragment_moshi) {

    private val simpleMovie = """
        {
            "title": "Star Wars: Episode IV - A New Hope",
            "year": 1977,
            "imdb_id": "tt0076759"
        }
    """

    private val movieList = """
        [
            {
                "title": "Star Wars: Episode IV - A New Hope",
                "year": 1977,
                "rating": "PG",
                "imdb_id": "tt0076759"
            },
            {
                "title": "The Lord of the Rings: The Fellowship of the Ring",
                "year": 2001,
                "rating": "G",
                "imdb_id": "tt0120737"
            }
        ]
    """

    private val movieWithRating = """
        {
            "title": "Star Wars: Episode IV - A New Hope",
            "year": 1977,
            "imdb_id": "tt0076759",
            "rating": "PG",
            "scores":[
                {"source":"Internet Movie Database","value":"8.6/10"},
                {"source":"Rotten Tomatoes","value":"92%"},
                {"source":"Metacritic","value":"90/100"}
            ]
        }
    """

    private val movieCustomAdapter = """
        {
            "main_info": {
                "title": "Star Wars: Episode IV - A New Hope",
                "year": 1977,
                "imdb_id": "tt0076759"
            },
            "additional_info": {
                "rating": "PG",
                "scores":[
                    {"source":"Internet Movie Database","value":"8.6/10"},
                    {"source":"Rotten Tomatoes","value":"92%"},
                    {"source":"Metacritic","value":"90/100"}
                ]
            }
        }
    """

    override fun onResume() {
        super.onResume()
        convertCustomMovieToJson()
    }

    private fun convertSimpleMovieJsonToInstance() {
        val moshi = Moshi.Builder().build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        try {
            val movie = adapter.fromJson(simpleMovie)
            textView.text = movie.toString()
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }

    }

    private fun convertMovieListJsonToInstance() {
        val moshi = Moshi.Builder().build()

        val movieListType = Types.newParameterizedType(
            List::class.java,
            Movie::class.java
        )

        val adapter = moshi.adapter<List<Movie>>(movieListType).nonNull()

        try {
            val movies = adapter.fromJson(movieList)
            textView.text = movies.toString()
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }

    }

    private fun convertMovieWithScoreJsonToInstance() {
        val moshi = Moshi.Builder().build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        try {
            val movie = adapter.fromJson(movieWithRating)
            textView.text = movie.toString()
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }
    }

    private fun convertMovieWithScoreInstanceToJson() {
        val movieToSerialize = Movie(
            id = "tt0076759",
            title = "Star Wars: Episode IV - A New Hope",
            year = 1977,
            rating = MovieRating.PG,
            scores = listOf(
                Score(source = "Internet Movie Database", value = "8.6/10")
            )
        )

        val moshi = Moshi.Builder().build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        try {
            val movieJson = adapter.toJson(movieToSerialize)
            textView.text = movieJson
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }

    }

    private fun convertCustomMovieToJson() {
        val moshi = Moshi.Builder()
            .add(MovieCustomAdapter())
            .build()

        val adapter = moshi.adapter(Movie::class.java).nonNull()

        try {
            val movie = adapter.fromJson(simpleMovie)
            textView.text = movie.toString()
        } catch (e: Exception) {
            textView.text = "parse error = ${e.message}"
        }
    }
}