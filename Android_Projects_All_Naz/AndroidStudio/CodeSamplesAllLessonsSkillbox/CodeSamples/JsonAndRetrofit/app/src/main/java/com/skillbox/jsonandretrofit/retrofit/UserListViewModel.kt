package com.skillbox.jsonandretrofit.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserListViewModel: ViewModel() {

    private val repository = UserRepository()

    private val userListLiveData = MutableLiveData<List<RemoteUser>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)

    val userList: LiveData<List<RemoteUser>>
        get() = userListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(query: String) {
        isLoadingLiveData.postValue(true)
        repository.searchUsers(
            query = query,
            onComplete = { users ->
                isLoadingLiveData.postValue(false)
                userListLiveData.postValue(users)
            },
            onError = { throwable ->
                isLoadingLiveData.postValue(false)
                userListLiveData.postValue(emptyList())
            }
        )
    }
}