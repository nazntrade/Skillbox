package com.becker.beckerSkillCinema.data.network

import com.becker.beckerSkillCinema.utils.ConstantsAndParams
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Retrofit {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ConstantsAndParams.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
}