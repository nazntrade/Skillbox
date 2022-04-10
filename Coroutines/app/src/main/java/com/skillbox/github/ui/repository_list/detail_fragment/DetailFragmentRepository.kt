package com.skillbox.github.ui.repository_list.detail_fragment

import android.util.Log
import com.skillbox.github.data.Networking
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class DetailFragmentRepository {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("ErrorCancelFragment", "error from CoroutineExceptionHandler", throwable)
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)


    var haveStarFromRepository by Delegates.notNull<Boolean>()
//    var errorFromRepository = ""
//    var onFailureFromRepository = false

    fun giveStarRepository(owner: String, repo: String) {
        Networking.gitHubApi.giveStar(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
//                        haveStarFromRepository = true
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
//                    onFailureFromRepository = true
//                    errorFromRepository = "Method 'give star' was failure: $t"
                }
            }
        )
    }

    fun takeAwayStarRepository(owner: String, repo: String) {
        Networking.gitHubApi.takeAwayStar(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
//                        haveStarFromRepository = false
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
//                    onFailureFromRepository = true
//                    errorFromRepository = "Method 'take away star' was failure: $t"
                }
            }
        )
    }

    suspend fun checkStarRepository(owner: String, repo: String): Boolean {
        scope.launch {
            suspendCancellableCoroutine<Boolean> { continuation ->
                continuation.invokeOnCancellation {
                    Networking.gitHubApi.checkStar(owner, repo).enqueue(
                        object : Callback<Boolean> {
                            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                if (response.isSuccessful)
                                    if (response.code() == 204) {
                                        haveStarFromRepository = true
                                        Log.d("aaaa", "response comme back $haveStarFromRepository")

                                    }
                            }
                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                Log.d("aaaa", "onFailure $t")
                            }
                        }
                    )
                    Log.d("aaaa", "end coroutines")
                }
            }
        }
        Log.d("aaaa", "return $haveStarFromRepository")
        return haveStarFromRepository
    }
}
