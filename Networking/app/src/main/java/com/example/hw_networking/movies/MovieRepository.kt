package com.example.hw_networking.movies

import android.util.Log
import com.example.hw_networking.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {

    fun searchMovie(
        queryTitleText: String,
        queryYearText: String,
        queryTypeText: String,
        callback: (List<RemoteMovie>) -> Unit
    ): Call {
        return Network.getSearchMovieCall(queryTitleText, queryYearText, queryTypeText).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback(emptyList())
                    // Some message for user
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val movies = doParseMovieResponse(responseString)
                        callback(movies)
                    } else {
                        callback(emptyList())
                        // Some message for user
                    }
                }
            })
        }
    }

    private fun doParseMovieResponse(responseString: String): List<RemoteMovie> {
        return try {
            val jsonObject = JSONObject(responseString)
            val movieArray = jsonObject.getJSONArray("Search") // Perhaps error

            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->
                    val imdbID = movieJsonObject.getString("imdbID")
                    val poster = movieJsonObject.getString("Poster")
                    val title = movieJsonObject.getString("Title")
                    val type = movieJsonObject.getString("Type")
                    val year = movieJsonObject.getString("Year")
                    val country = movieJsonObject.getString("Country")
                    val runtime = movieJsonObject.getString("Runtime")
                    val imdbRating = movieJsonObject.getString("imdbRating")
                    val genre = movieJsonObject.getString("Genre")
                    val plot = movieJsonObject.getString("Plot")

                    RemoteMovie(
                        imdbID = imdbID,
                        poster = poster,
                        title = title,
                        type = type,
                        year = year,
                        country = country,
                        runtime = runtime,
                        imdbRating = imdbRating,
                        genre = genre,
                        plot = plot
                    )
                }
        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList()
            // Some message for user
        }
    }
}