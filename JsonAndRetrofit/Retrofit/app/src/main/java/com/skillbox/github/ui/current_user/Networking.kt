package com.skillbox.github.ui.current_user

import com.skillbox.github.data.Retrofit.retrofit
import com.skillbox.github.ui.repository_list.GitHubRepositoriesApi
import retrofit2.create

object Networking {

    val gitHubUsersApi: GitHubUsersApi
        get() = retrofit.create()

    val gitHubRepositoriesApi: GitHubRepositoriesApi
    get() = retrofit.create()

}