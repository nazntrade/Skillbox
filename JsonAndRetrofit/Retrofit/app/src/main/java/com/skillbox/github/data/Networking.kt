package com.skillbox.github.data

import com.skillbox.github.data.Retrofit.retrofit
import retrofit2.create

object Networking {

    val gitHubApi: GitHubApi
        get() = retrofit.create()
}