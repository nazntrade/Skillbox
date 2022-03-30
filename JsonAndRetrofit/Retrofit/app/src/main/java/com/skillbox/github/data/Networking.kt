package com.skillbox.github.data

import com.skillbox.github.data.Retrofit.retrofit
import com.skillbox.github.ui.current_user.GitHubUsersApi
import com.skillbox.github.ui.repository_list.GitHubRepApi
import retrofit2.create

object Networking {

    val gitHubUsersApi: GitHubUsersApi
        get() = retrofit.create()

    val gitHubRepApi: GitHubRepApi
        get() = retrofit.create()
}