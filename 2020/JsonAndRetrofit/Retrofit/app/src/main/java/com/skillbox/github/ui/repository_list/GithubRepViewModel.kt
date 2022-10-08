package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GithubRepViewModel : ViewModel() {

    private val repository = GithubRepRepository()
    private val repoListLiveData = MutableLiveData<List<Repositories>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val repoList: LiveData<List<Repositories>>
        get() = repoListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getRepoListFromViewModel() {
        isLoadingLiveData.postValue(true)
        repository.getRepoListFromRepository(   ////???????
            onComplete = { repoList ->
                isLoadingLiveData.postValue(false)
                repoListLiveData.postValue(repoList)
            },
            onError = {
                isLoadingLiveData.postValue(false)
                repoListLiveData.postValue(emptyList())
            }
        )
    }
}