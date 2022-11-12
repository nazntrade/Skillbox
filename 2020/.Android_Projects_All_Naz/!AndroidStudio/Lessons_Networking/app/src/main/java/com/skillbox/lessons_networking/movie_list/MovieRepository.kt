package com.skillbox.lessons_networking.movie_list

import android.util.Log
import com.skillbox.lessons_networking.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

class MovieRepository {
//  1.0 begin work with request from server
    fun searchMovie(text: String, callback: (List<RemoteMovie>) -> Unit): Call {   // : Call  -> 3.3 in order to cancel all process when user stopped request
        return Network.getSearchMovieCall(text).apply {
            //  1.10
            enqueue(object : Callback { // L25.3 t43.19
                override fun onFailure(call: Call, e: java.io.IOException) {
                    // if the server response is a failure
                    Log.e("Server", "execute request error = ${e.message}", e)
                    callback(emptyList())
                }

                //  getting the body response from server
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
                        //  1.11
                        val responseString = response.body?.string().orEmpty()
                        //  and parse it
                        val movies = parseMovieResponse(responseString)
                        callback(movies)
                    } else {
                        // if the server response isn't successful
                        callback(emptyList()) //if something going wrong we hand over empty callback in order to loadingBar is gone
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

    // 1.12
    private fun parseMovieResponse(responseBodyString: String): List<RemoteMovie> {
        return try { // try catch in order to handle errors json
            val jsonObject = JSONObject(responseBodyString)   // get json from response server. in order to use fields "title, year" e.t.c
            val movieArray = jsonObject.getJSONArray("Search")

            // iteration on each object and create list json objects Les25.3 t38.15
            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject -> // create object Movie from jsonList
                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val id = movieJsonObject.getString("imdbID")
                    RemoteMovie(id = id, title = title, year = year)
                }

        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList() // in order to not crash
        }
    }
}