package com.skillbox.github.ui.repository_list

import com.skillbox.github.data.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepRepository {

    fun getRepoListFromRepository(
        onComplete: (List<Repositories>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.gitHubApi.getOpenRepositories().enqueue(
            object : Callback<List<Repositories>> {
                override fun onResponse(
                    call: Call<List<Repositories>>,
                    response: Response<List<Repositories>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body().orEmpty())
                    }
                }

                override fun onFailure(call: Call<List<Repositories>>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}