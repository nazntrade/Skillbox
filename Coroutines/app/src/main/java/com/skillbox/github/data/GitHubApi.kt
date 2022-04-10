package com.skillbox.github.data

import com.skillbox.github.ui.current_user.CurrentUser
import com.skillbox.github.ui.repository_list.Repositories
import retrofit2.Call
import retrofit2.http.*

interface GitHubApi {

    @GET("user")
    fun getDataCurrentUser(): Call<CurrentUser>

    @GET("repositories")
    fun getOpenRepositories(): Call<List<Repositories>>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<Boolean>

    @PUT("user/starred/{owner}/{repo}")
    fun giveStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<String>

    @DELETE("user/starred/{owner}/{repo}")
    fun takeAwayStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<String>

    @GET("users/{username}/starred")
    fun getStaredRepositories(
        @Path("username") username: String
    ): Call<List<Repositories>>

}