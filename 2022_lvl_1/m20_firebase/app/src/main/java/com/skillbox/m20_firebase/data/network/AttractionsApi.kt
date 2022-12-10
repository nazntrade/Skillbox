package com.skillbox.m20_firebase.data.network

import com.skillbox.m20_firebase.data.models.AttractionModel
import retrofit2.http.*

//https://api.opentripmap.com/0.1/en/places/radius?radius=1000&lon=-79.0538864&lat=43.0974998&apikey=5ae2e3f221c38a28845f05b6231408dc3b90140f6001e84501c5091c

interface AttractionsApi {

    @GET("en/places/radius")
    suspend fun getAttractions(
        @Query("radius") radius: Int,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("apikey") apikey: String

    ): ServerItemsWrapper<AttractionModel>
}