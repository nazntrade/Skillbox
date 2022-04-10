package com.skillbox.github.ui.repository_list.detail_fragment

import android.util.Log
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

    private val errorLiveData = MutableLiveData<String>()
    val errorFromViewModel: LiveData<String>
        get() = errorLiveData

    private val onFailureLiveData = MutableLiveData<Boolean>()
    val onFailureFromViewModel: LiveData<Boolean>
        get() = onFailureLiveData

    fun giveStarViewModel(owner: String, repo: String) {
        repository.giveStarRepository(owner, repo)
//        updateAllLifeData()
    }

    fun takeAwayStarViewModel(owner: String, repo: String) {
        repository.takeAwayStarRepository(owner, repo)
//        updateAllLifeData()
    }

    fun checkStarViewModel(owner: String, repo: String){
        viewModelScope.launch {
            try {
                val haveStar = repository.checkStarRepository(owner, repo)
                haveStarLiveData.postValue(haveStar)
                Log.d("aaaa","${repository.haveStarFromRepository}")

            } catch (t: Throwable) {
//                errorLiveData.postValue(repository.errorFromRepository)
            }finally {
            }
        }
    }

//    private fun updateAllLifeData() {
//        haveStarLiveData.postValue(repository.haveStarFromRepository)
////        errorLiveData.postValue(repository.errorFromRepository)
////        onFailureLiveData.postValue(repository.onFailureFromRepository)
//    }
}