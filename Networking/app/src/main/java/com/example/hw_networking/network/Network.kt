package com.example.hw_networking.network

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    private val client = OkHttpClient.Builder()
//        .addNetworkInterceptor(
//            //  1.6
//            CustomHeaderInterceptor("apikey", API_KEY)
//        )
//        .addNetworkInterceptor(
//            CustomHeaderInterceptor("header2", "headerValue2")
//        )
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
//        //  1.8
//        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    fun getSearchMovieCall(
        queryTitleText: String,
        queryYearText: String,
        queryTypeText: String
    ): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=
        val movieUrl = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("apikey", API_KEY)
            .addQueryParameter("s", queryTitleText)
            .addQueryParameter("y", queryYearText)
            .addQueryParameter("type", queryTypeText)
            .build()

        val request = Request.Builder()
            .get()
            .url(movieUrl)
            .build()

        return client.newCall(request)
    }
}