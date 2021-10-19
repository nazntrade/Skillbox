package com.skillbox.networking.movie_list

import android.util.Log
import com.skillbox.networking.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

class MovieRepository {

    fun searchMovie(text: String, callback: (List<RemoteMovie>) -> Unit): Call {
        return Network.getSearchMovieCall(text).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: java.io.IOException) {
                    Log.e("Server", "execute request error = ${e.message}", e)
                    callback(emptyList())
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val movies = parseMovieResponse(responseString)
                        callback(movies)
                    } else {
                        callback(emptyList())
                    }
                }
            })
        }

//            try {
//                val response = Network.getSearchMovieCall(text).execute()
//                val responseString = response.body?.string().orEmpty()
//                val movies = parseMovieResponse(responseString)
//                callback(movies)
//            } catch (e: IOException) {
//                Log.e("Server", "execute request error = ${e.message}", e)
//                callback(emptyList())
//            }
    }

    private fun parseMovieResponse(responseBodyString: String): List<RemoteMovie> {
        return try {
            val jsonObject = JSONObject(responseBodyString)
            val movieArray = jsonObject.getJSONArray("Search")

            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->
                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val id = movieJsonObject.getString("imdbID")
                    RemoteMovie(id = id, title = title, year = year)
                }

        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList()
        }
    }
}