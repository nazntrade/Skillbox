package com.skillbox.lessons_multithreading.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {

    const val MOVIE_API_KEY = "http://www.omdbapi.com/?i=tt3896198&apikey=7618da2"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://www.omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun api(): MovieApi {
        return retrofit.create()
    }
}