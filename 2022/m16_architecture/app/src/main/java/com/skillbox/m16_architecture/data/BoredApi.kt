package com.skillbox.m16_architecture.data

import retrofit2.http.*

interface BoredApi {

    @GET("activity")
    suspend fun getEntertainment(): Entertainment

}