package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GithubRepViewModel : ViewModel() {

    private val repository = GithubRepRepository()
    private val repoListLiveData = MutableLiveData<List<Repositories>>(emptyList())

    val repoList: LiveData<List<Repositories>>
        get() = repoListLiveData

    fun getRepoListFromViewModel() {
        repository.getRepoListFromRepository(   ////???????
            onComplete = { repoList ->
                repoListLiveData.postValue(repoList)
            },
            onError = { throwable ->
//                isLoadingLiveData.postValue(false)
                repoListLiveData.postValue(emptyList())
            }
        )
    }
}