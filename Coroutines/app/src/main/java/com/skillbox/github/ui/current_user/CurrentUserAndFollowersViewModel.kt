package com.skillbox.github.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CurrentUserAndFollowersViewModel : ViewModel() {
    private val repository = CurrentUserAndFollowingRepository()

    private val currentUserLiveData = MutableLiveData<CurrentUser>()
    val currentUser: LiveData<CurrentUser>
        get() = currentUserLiveData

    private val followingListLiveData = MutableLiveData<List<UserFollowing>>()
    val followingList: LiveData<List<UserFollowing>>
        get() = followingListLiveData


    fun getCurrentDataViewModel() {
        viewModelScope.launch {
            try {
                val userData = repository.getCurrentDataRepository()
                currentUserLiveData.postValue(userData)

            } catch (t: Throwable) {
                Log.d("getCurrentDataViewModel", "$t")
            }
        }
    }

    fun getFollowingViewModel() {
        viewModelScope.launch {
            try {
                val followings = repository.getFollowingList()
                followingListLiveData.postValue(followings)
            } catch (t: Throwable) {
                Log.d("getFollowingViewModel", "$t")
            }
        }
    }
}