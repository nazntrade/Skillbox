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
            object : Callback<List<Repositories>> {
                override fun onResponse(
                    call: Call<List<Repositories>>,
                    response: Response<List<Repositories>>
                ) {
                    Log.d("aaaa", "onResponseStart")

                    if (response.isSuccessful) {
                        Log.d("aaaa", "response is Successful")

                        onComplete(response.body().orEmpty())

                        Log.d("aaaa", "${response.body()}")
                    }
                }

                override fun onFailure(call: Call<List<Repositories>>, t: Throwable) {
                    Log.d("aaaa", "onFailure")
                    onError(t)
                }
            }
        )
    }
}