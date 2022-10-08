package com.skillbox.jsonandretrofit.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<ServerItemsWrapper<RemoteUser>>
}