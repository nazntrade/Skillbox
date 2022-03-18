package com.example.hw_networking.movies

import android.util.Log
import com.example.hw_networking.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
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
            enqueue(object : Callback {  //enqueue ????????
                override fun onFailure(call: Call, e: IOException) {
                    error = "IOException"
                    Log.e("Server", "IOException")
                    callback(emptyList())
                }

                override fun onResponse(call: Call, response: Response) {
                    error = ""
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()

                        val moshi = Moshi.Builder()
                            .build()

                        val movieListType = Types.newParameterizedType(
                            List::class.java,
                            RemoteMovie::class.java
                        )

                        val adapter = moshi.adapter<List<RemoteMovie>>(movieListType).nonNull()

                        try {
                            val movies = adapter.fromJson(responseString)
                            Log.d("error", "movies = $movies")
                            if (movies != null) {
                                callback(movies)
                            } else Log.d("error", "movie null")
                        } catch (e: Exception) {
                            Log.e("error", "${e.message}")
//                            error = "JSONException"
                            callback(emptyList())
                        }

                    } else {
                        error = "FailureServerResponse"
                        Log.e("Server", "FailureServerResponse")
                        callback(emptyList())
                    }
                }
            })
        }
    }

//    private fun doParseMovieResponse(
//        responseString: String
//    ): List<RemoteMovie> {
//        return try {
//            val jsonObject = JSONObject(responseString)
//            val movieArray = jsonObject.getJSONArray("Search")
//
//
////        } catch (e: JSONException) {
////            error = "JSONException"
////            Log.e("Server", "JSONException ${e.message}", e)
////            emptyList()
////        }
//    }
}