package com.example.hw_networking.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call

class MoviesViewModel : ViewModel() {

    private val repository = MovieRepository()
    private var currentCall: Call? = null
    private val moviesListLiveData = MutableLiveData<List<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val movies: LiveData<List<RemoteMovie>>
        get() = moviesListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(queryTitleText: String) {

//        currentCall = repository.searchMovie(queryTitleText) { movies ->
//            moviesListLiveData.postValue(movies)
//        }

    }
}