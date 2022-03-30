package com.skillbox.github.ui.repository_list

import retrofit2.Call
import retrofit2.http.GET

interface GitHubRepositoriesApi {
    @GET("repositories")
    fun getOpenRepositories(): Call<Repositories>

//: Call<ServerItemsWrapper<RemoteUser>>
// many objects are wrapped on owner
}