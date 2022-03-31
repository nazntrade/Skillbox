package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.data.Networking
import com.skillbox.github.data.ServerItemsWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepRepository {

    fun getRepoListFromRepository(
        onComplete: (List<Repositories>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.gitHubRepApi.getOpenRepositories().enqueue(
            object : Callback<ServerItemsWrapper<Repositories>> {
                override fun onResponse(
                    call: Call<ServerItemsWrapper<Repositories>>,
                    response: Response<ServerItemsWrapper<Repositories>>
                ) {
                    Log.d("aaaa", "onResponseStart")

                    if (response.isSuccessful) {
                        onComplete(response.body()?.items.orEmpty())
                    }
                }

                override fun onFailure(call: Call<ServerItemsWrapper<Repositories>>, t: Throwable) {
                    Log.d("aaaa", "onFailure")
                    onError(t)
                }
            }
        )
    }
}