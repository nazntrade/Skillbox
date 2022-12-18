package com.becker.beckerSkillCinema.data.network

import com.becker.beckerSkillCinema.data.network.Retrofit.retrofit
import retrofit2.create

object Networking {

    val kinopoiskApi: KinopoiskApi
        get() = retrofit.create()
//        get() = retrofit.create(KinopoiskApi::class.java)
}