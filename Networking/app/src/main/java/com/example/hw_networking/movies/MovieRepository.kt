package com.example.hw_networking.movies

import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.hw_networking.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {
    var error = ""
    fun searchMovie(
        queryTitleText: String,
        queryYearText: String,
        queryTypeText: String,
        callback: (List<RemoteMovie>) -> Unit
    ): Call {
        error = ""
        return Network.getSearchMovieCall(queryTitleText, queryYearText, queryTypeText).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    error = "IOException"
                    Log.e("Server", "IOException")
                    callback(emptyList())
                }

                override fun onResponse(call: Call, response: Response) {
                    error = ""
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val movies = doParseMovieResponse(responseString)
                        callback(movies)
                    } else {
                        error = "FailureServerResponse"
                        Log.e("Server", "FailureServerResponse")
                        callback(emptyList())
                    }
                }
            })
        }
    }

    private fun doParseMovieResponse(
        responseString: String
    ): List<RemoteMovie> {
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

                    RemoteMovie(
                        imdbID = imdbID,
                        poster = poster,
                        title = title,
                        type = type,
                        year = year,
                    )
                }
        } catch (e: JSONException) {
            error = "JSONException"
            Log.e("Server", "JSONException ${e.message}", e)
            emptyList()
        }
    }
}