package com.skillbox.github.data

import com.skillbox.github.ui.current_user.CurrentUser
import com.skillbox.github.ui.current_user.UserFollowing
import com.skillbox.github.ui.repository_list.Repositories
import retrofit2.Call
import retrofit2.http.*

interface GitHubApi {

    @GET("user")
    suspend fun getDataCurrentUser(): CurrentUser

    @GET("user/following")
    suspend fun getFollowing(): List<UserFollowing>

    @GET("repositories")
    fun getOpenRepositories(): Call<List<Repositories>>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<Boolean>

    @PUT("user/starred/{owner}/{repo}")
    suspend fun giveStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    )

    @DELETE("user/starred/{owner}/{repo}")
    suspend fun takeAwayStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    )
}