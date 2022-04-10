package com.skillbox.github.ui.repository_list.detail_fragment

import android.util.Log
import com.skillbox.github.data.Networking
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException
import kotlin.properties.Delegates

class DetailFragmentRepository {

    var errorFromRepository = ""

    suspend fun giveStarRepository(owner: String, repo: String) {
        withContext(Dispatchers.IO) {
            Networking.gitHubApi.giveStar(owner, repo)
        }
    }

    suspend fun takeAwayStarRepository(owner: String, repo: String) {
        withContext(Dispatchers.IO) {
            Networking.gitHubApi.takeAwayStar(owner, repo)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun checkStarRepository(owner: String, repo: String): Boolean {
        suspendCancellableCoroutine<Boolean> { continuation ->
            Networking.gitHubApi.checkStar(owner, repo).enqueue(
                object : Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if (response.isSuccessful) {
                            continuation.resume(response.code() == 204, onCancellation = null)
                        } else {
                            continuation.resumeWithException(RuntimeException("incorrect status code"))
                            errorFromRepository = "incorrect response status code from server"
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        continuation.resumeWithException(t)
                        Log.d("checkStarRepository", "onFailure $t")
                        errorFromRepository = "server response is failure $t"
                    }
                }
            )
        }
        return true
    }
}
