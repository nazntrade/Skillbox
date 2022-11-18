package com.skillbox.m19_location.data.network

import com.skillbox.m19_location.data.models.AttractionsModel
import retrofit2.http.*

interface AttractionsApi {

    @GET("some")
    suspend fun getAttractions(): AttractionsModel
}