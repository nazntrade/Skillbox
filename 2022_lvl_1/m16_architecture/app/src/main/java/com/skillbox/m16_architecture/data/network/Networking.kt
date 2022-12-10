package com.skillbox.m16_architecture.data.network

import com.skillbox.m16_architecture.data.network.Retrofit.retrofit
import retrofit2.create

object Networking {

    val boredApi: BoredApi
        get() = retrofit.create()
}