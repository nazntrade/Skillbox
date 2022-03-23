package com.example.hw_networking.network

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            CustomInterceptor("apikey", API_KEY)
        )
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    fun getSearchMovieCall(
        queryTitleText: String,
        queryYearText: String,  // don't use
        queryTypeText: String   // don't use
    ): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=
        val movieUrl = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter(
                "t",
                queryTitleText
            )// s to t for this project. required in assignment
//            .addQueryParameter("y", queryYearText)
//            .addQueryParameter("type", queryTypeText)
            .build()

        val request = Request.Builder()
            .get()
            .url(movieUrl)
            .build()

        return client.newCall(request)
    }
}