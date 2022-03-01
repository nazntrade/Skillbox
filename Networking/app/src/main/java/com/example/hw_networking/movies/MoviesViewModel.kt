package com.example.hw_networking.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call

class MoviesViewModel : ViewModel() {

    private val repository = MovieRepository()

    private var currentCall: Call? = null

    private val moviesListLiveData = MutableLiveData<List<RemoteMovie>>()

    val movies: LiveData<List<RemoteMovie>>
        get() = moviesListLiveData

    fun search(queryTitleText: String) {

        currentCall = repository.searchMovie(queryTitleText) { movies ->
            moviesListLiveData.postValue(movies)
        }

    }
}