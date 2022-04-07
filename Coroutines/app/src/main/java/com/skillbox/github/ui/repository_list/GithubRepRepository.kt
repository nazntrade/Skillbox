package com.skillbox.github.ui.repository_list

import com.skillbox.github.data.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepRepository {

    suspend fun getRepoListFromRepository(
    ) : List<Repositories> {
        withContext(Dispatchers.Default){
            Networking.gitHubApi.getOpenRepositories().execute(
                object : Callback<List<Repositories>> {
                    override fun onResponse(
                        call: Call<List<Repositories>>,
                        response: Response<List<Repositories>>
                    ) {
                        if (response.isSuccessful) {
                            (response.body().orEmpty())
                        }
                    }

                    override fun onFailure(call: Call<List<Repositories>>, t: Throwable) {
                        onError(t)
                    }
                }
            )
        }
    }
}