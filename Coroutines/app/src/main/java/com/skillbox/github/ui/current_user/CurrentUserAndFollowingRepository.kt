package com.skillbox.github.ui.current_user

import android.util.Log
import com.skillbox.github.data.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrentUserAndFollowingRepository {

    suspend fun getCurrentDataRepository(): CurrentUser {
        return withContext(Dispatchers.IO) {
            Networking.gitHubApi.getDataCurrentUser()
        }
    }

    suspend fun getFollowingList(): List<UserFollowing> {
        Log.d("aaaa", "start getFollowersList")
        return withContext(Dispatchers.IO) {
            Networking.gitHubApi.getFollowing()
        }
    }
}