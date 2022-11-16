package com.skillbox.lessons_networking.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.skillbox.lessons_networking.movie_list.API_KEY
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    //  1.4
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            //  1.6
            CustomHeaderInterceptor("header1", "headerValue1")
        )
        .addNetworkInterceptor(
            CustomHeaderInterceptor("header2", "headerValue2")
        )
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        //  1.8
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    //  1.1
    fun getSearchMovieCall(text: String): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=
        //  1.3
        val url = HttpUrl.Builder()
            .scheme("http")
            // add param to manifest :
            // android:usesCleartextTraffic="true"
            // android:networkSecurityConfig="" : https://developer.android.com/training/articles/security-config#CleartextTrafficPermitted
            .host("www.omdbapi.com")
            .addQueryParameter("apikey", API_KEY)
            .addQueryParameter("s", text)
            .build()
        //  1.2
        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        //  1.5
        //  1.9
        return client.newCall(request)
    }

}