package com.skillbox.m16_architecture.data.network

import com.skillbox.m16_architecture.data.entity.UsefulActivity
import retrofit2.http.*

interface BoredApi {

    @GET("activity")
    suspend fun getUsefulActivity(): UsefulActivity
}