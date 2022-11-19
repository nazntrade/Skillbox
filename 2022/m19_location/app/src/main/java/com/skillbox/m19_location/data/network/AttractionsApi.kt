package com.skillbox.m19_location.data.network

import com.skillbox.m19_location.data.models.AttractionModel
import retrofit2.http.*

//https://api.opentripmap.com/0.1/en/places/radius?radius=1000&lon=-79.0538864&lat=43.0974998&apikey=5ae2e3f221c38a28845f05b6231408dc3b90140f6001e84501c5091c

interface AttractionsApi {

    @GET("en/places/radius?radius=5000&lon={lon}&lat={lat}&apikey={apikey}")
    suspend fun getAttractions(
        @Path("lon") lon: Double,
        @Path("lat") lat: Double,
        @Path("apikey") apikey: String

    ): List<AttractionModel>


}