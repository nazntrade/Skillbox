package com.skillbox.github.ui.repository_list

import com.skillbox.github.data.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepRepository {
    suspend fun getRepoListFromRepository(): List<Repositories>? {
        return withContext(Dispatchers.IO) {
            Networking.gitHubApi.getOpenRepositories().execute().body()
        }
    }
}