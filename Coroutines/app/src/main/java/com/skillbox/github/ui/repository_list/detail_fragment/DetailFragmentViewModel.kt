package com.skillbox.github.ui.repository_list.detail_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class DetailFragmentViewModel : ViewModel() {

    private val repository = DetailFragmentRepository()

    private val haveStarLiveData = MutableLiveData<Boolean>()
    val haveStarFromViewModel: LiveData<Boolean>
        get() = haveStarLiveData

    private val errorLiveData = MutableLiveData<String>()
    val errorFromViewModel: LiveData<String>
        get() = errorLiveData

    private val onFailureLiveData = MutableLiveData<Boolean>()
    val onFailureFromViewModel: LiveData<Boolean>
        get() = onFailureLiveData

    fun giveStarViewModel(owner: String, repo: String) {
        repository.giveStarRepository(owner, repo)
        updateLifeDates()
    }

    fun takeAwayStarViewModel(owner: String, repo: String) {
        repository.takeAwayStarRepository(owner, repo)
        updateLifeDates()
    }

    fun checkStarViewModel(owner: String, repo: String) {
        repository.checkStarRepository(owner, repo)
        updateLifeDates()
    }

    private fun updateLifeDates() {
        haveStarLiveData.postValue(repository.haveStarFromRepository)
        errorLiveData.postValue(repository.errorFromRepository)
        onFailureLiveData.postValue(repository.onFailureFromRepository)
    }
}