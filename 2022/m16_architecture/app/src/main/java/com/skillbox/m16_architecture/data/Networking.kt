package com.skillbox.m16_architecture.data

import com.skillbox.m16_architecture.data.Retrofit.retrofit
import retrofit2.create

object Networking {

    val boredApi: BoredApi
        get() = retrofit.create()
}