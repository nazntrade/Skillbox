package com.skillbox.m20_firebase.data.network

import com.skillbox.m20_firebase.data.network.Retrofit.retrofit
import retrofit2.create

object Networking {

    val attractionsApi: AttractionsApi
        get() = retrofit.create()
}