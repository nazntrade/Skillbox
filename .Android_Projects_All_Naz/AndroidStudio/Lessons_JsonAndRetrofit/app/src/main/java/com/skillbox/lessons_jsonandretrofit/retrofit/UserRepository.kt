package com.skillbox.lessons_jsonandretrofit.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun searchUsers(
        query: String,
        onComplete: (List<RemoteUser>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.searchUsers(query).enqueue(
            object : Callback<ServerItemsWrapper<RemoteUser>> {

                override fun onFailure(call: Call<ServerItemsWrapper<RemoteUser>>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(
                    call: Call<ServerItemsWrapper<RemoteUser>>,
                    response: Response<ServerItemsWrapper<RemoteUser>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.items.orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }
            }
        )
    }
}