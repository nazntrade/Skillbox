package com.skillbox.m19_location.data.network

import com.skillbox.m19_location.data.network.Retrofit.retrofit
import retrofit2.create

object Networking {

    val attractionsApi: AttractionsApi
        get() = retrofit.create()
}