package com.skillbox.github.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class CurrentUserAndFollowersViewModel : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("ErrorCancelFragment", "error from CoroutineExceptionHandler", throwable)
    }
    private val modifiedViewModelScope = (viewModelScope + errorHandler)

    private val repository = CurrentUserAndFollowingRepository()

    private val currentUserLiveData = MutableLiveData<CurrentUser>()
    val currentUser: LiveData<CurrentUser>
        get() = currentUserLiveData

    private val followingListLiveData = MutableLiveData<List<UserFollowing>>()
    val followingList: LiveData<List<UserFollowing>>
        get() = followingListLiveData

    private val isLoadingLiveData = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getInfoAsyncViewModel() {
        modifiedViewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                (0 until 10).map {
                    async {
                        val userData = repository.getCurrentDataRepository()
                        val followings = repository.getFollowingList()
                        currentUserLiveData.postValue(userData)
                        followingListLiveData.postValue(followings)
                    }
                }.map {
                    it.await()
                }
            } catch (t: Throwable) {
                Log.d("getInfoAsyncViewModel", "$t")
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}