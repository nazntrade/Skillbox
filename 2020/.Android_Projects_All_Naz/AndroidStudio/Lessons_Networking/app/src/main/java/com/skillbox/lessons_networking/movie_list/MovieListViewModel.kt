package com.skillbox.lessons_networking.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()

    private var currentCall: Call? = null    //3.3 in order to cancel all process when user stopped request

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val movieList: LiveData<List<RemoteMovie>>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(text: String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(text) { movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
            currentCall = null   //3.3 in order to cancel all process when user stopped request
        }
    }

    override fun onCleared() {   //3.3 in order to cancel all process when user stopped request
        super.onCleared()
        currentCall?.cancel()
    }
}