package com.skillbox.hw_scopedstorage.data

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.create

object Networking {
    private val okhttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .followRedirects(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(okhttpClient)
        .build()

    val api: Api
        get() = retrofit.create()

}
