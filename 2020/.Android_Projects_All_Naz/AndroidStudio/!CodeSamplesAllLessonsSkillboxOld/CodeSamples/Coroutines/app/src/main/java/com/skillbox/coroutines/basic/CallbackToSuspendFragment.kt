package com.skillbox.coroutines.basic

import android.util.Log
import androidx.fragment.app.Fragment
import com.skillbox.coroutines.data.Networking
import com.skillbox.coroutines.data.RemoteUser
import com.skillbox.coroutines.data.ServerItemsWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CallbackToSuspendFragment: Fragment() {

    private val fragmentScope = CoroutineScope(Dispatchers.Main)

    override fun onResume() {
        super.onResume()
        fragmentScope.launch {
            try {
                val users = searchUsers()
                Log.d("CallbackToSuspend", "users = $users")
            } catch (t: Throwable) {
                Log.e("CallbackToSuspend", "error", t)
            }
        }
    }

    private suspend fun searchUsers(): List<RemoteUser> {
        return suspendCoroutine<List<RemoteUser>> { continuation ->
            Networking.githubApi.searchUsers("test").enqueue(object : Callback<ServerItemsWrapper<RemoteUser>> {
                override fun onFailure(call: Call<ServerItemsWrapper<RemoteUser>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(
                    call: Call<ServerItemsWrapper<RemoteUser>>,
                    response: Response<ServerItemsWrapper<RemoteUser>>
                ) {
                    if(response.isSuccessful) {
                        continuation.resume(response.body()?.items.orEmpty())
                    } else {
                        continuation.resumeWithException(RuntimeException("incorrect status code"))
                    }
                }
            })
        }


    }

}