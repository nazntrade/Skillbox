package com.skillbox.github.ui.repository_list.detail_fragment

import com.skillbox.github.data.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragmentRepository {

    var haveStarFromRepository = false
    var errorFromRepository = ""
    var onFailureFromRepository = false

    fun giveStarRepository(owner: String, repo: String) {
        Networking.gitHubApi.giveStar(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        haveStarFromRepository = true
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onFailureFromRepository = true
                    errorFromRepository = "Method 'give star' was failure: $t"
                }
            }
        )
    }

    fun takeAwayStarRepository(owner: String, repo: String) {
        Networking.gitHubApi.takeAwayStar(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        haveStarFromRepository = false
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onFailureFromRepository = true
                    errorFromRepository = "Method 'take away star' was failure: $t"
                }
            }
        )
    }

    fun checkStarRepository(owner: String, repo: String) {
        Networking.gitHubApi.checkStar(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        if (response.code() == 204) {
                            haveStarFromRepository = true
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onFailureFromRepository = true
                    errorFromRepository = "The checking star was failure: $t"
                }
            }
        )
    }
}
