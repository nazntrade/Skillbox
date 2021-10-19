package com.skillbox.coroutines.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepository {

    suspend fun searchUsers(
        query: String
    ): List<RemoteUser> {
        return withContext(Dispatchers.IO) {
            Networking.githubApi.searchUsersSuspend(query).items
        }
    }
}
