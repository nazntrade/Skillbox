package com.skillbox.github.data

import com.skillbox.github.ui.current_user.AuthorisationCustomInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Retrofit {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            AuthorisationCustomInterceptor()
        )
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
}