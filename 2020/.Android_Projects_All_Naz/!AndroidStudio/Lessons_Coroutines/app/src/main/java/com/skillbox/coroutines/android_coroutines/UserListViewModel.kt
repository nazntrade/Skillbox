package com.skillbox.coroutines.android_coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.coroutines.data.RemoteUser
import com.skillbox.coroutines.data.UserRepository
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    private val repository = UserRepository()

    private val userListLiveData = MutableLiveData<List<RemoteUser>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)

    val userList: LiveData<List<RemoteUser>>
        get() = userListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(query: String) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val users = repository.searchUsers(query)
                userListLiveData.postValue(users)
            } catch (t: Throwable) {
                userListLiveData.postValue(emptyList())

            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}