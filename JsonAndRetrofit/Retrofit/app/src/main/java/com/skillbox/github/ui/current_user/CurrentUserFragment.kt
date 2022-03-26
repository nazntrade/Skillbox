package com.skillbox.github.ui.current_user

import androidx.fragment.app.Fragment
import com.skillbox.github.R
import com.skillbox.github.ui.GitHubApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class CurrentUserFragment : Fragment(R.layout.fragment_current_user) {


    data class currentUser(
        val id: Long,
        val login: String,
        val avatar_url: String
    )

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val gitHubApi: GitHubApi
        get() = retrofit.create()
}