package com.skillbox.github.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class GithubRepViewModel : ViewModel() {

    private val repository = GithubRepRepository()
    private val repoListLiveData = MutableLiveData<List<Repositories>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("ErrorCancelFragment", "error from CoroutineExceptionHandler", throwable)
    }

    //the myViewModelScope creates this only for practice. in all other cases in viewModel use viewModelScope. it cancels everything itself
    // in order to  ...   won't cancel with err.,  //establish UI thread, // handle errors
    private val myViewModelScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)

    val repoList: LiveData<List<Repositories>>
        get() = repoListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getRepoListFromViewModel() {
        viewModelScope.launch {
            yield()
            isLoadingLiveData.postValue(true)
            try {
                val repoList = repository.getRepoListFromRepository()
                repoListLiveData.postValue(repoList)
            } catch (t: Throwable) {
                repoListLiveData.postValue(emptyList())
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //this only for practice. in all other cases use standard 'viewModelScope'. it will cancel everything
    // yield() + override fun onCleared() {} = will cancel coroutines
    override fun onCleared() {
        super.onCleared()
        myViewModelScope.coroutineContext.cancel()
    }
}