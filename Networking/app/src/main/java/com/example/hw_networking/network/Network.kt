package com.example.hw_networking.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private val client = OkHttpClient.Builder()
//        // 1.6
        .addNetworkInterceptor(
            CustomInterceptor("apikey", API_KEY)
        )
//        //see in logcat query and response details
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
//        //  1.8
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
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
//            .addQueryParameter("apikey", API_KEY)
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