package com.skillbox.github.ui.repository_list.detail_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailFragmentViewModel : ViewModel() {

    private val repository = DetailFragmentRepository()

    private val haveStarLiveData = MutableLiveData<Boolean>()
    val haveStarFromViewModel: LiveData<Boolean>
        get() = haveStarLiveData

    private val messageLiveData = MutableLiveData<String>()
    val messageFromViewModel: LiveData<String>
        get() = messageLiveData

    fun giveStarViewModel(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                repository.giveStarRepository(owner, repo)
            } catch (t: Throwable) {
                messageLiveData.postValue("You gave a star.")
            }
        }
    }

    fun takeAwayStarViewModel(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                repository.takeAwayStarRepository(owner, repo)
            } catch (t: Throwable) {
                messageLiveData.postValue("You took a star away.")
            }
        }
    }

    fun checkStarViewModel(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val haveStar = repository.checkStarRepository(owner, repo)
                haveStarLiveData.postValue(haveStar)
                messageLiveData.postValue(repository.errorFromRepository)
            } catch (t: Throwable) {
                messageLiveData.postValue("You haven't given this repository a star yet.")
            }
        }
    }
}