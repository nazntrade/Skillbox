package com.skillbox.github.ui.current_user

import retrofit2.Call
import retrofit2.http.GET

interface GitHubUsersApi {
    @GET("user")
    fun getDataCurrentUser(): Call<CurrentUser>
}