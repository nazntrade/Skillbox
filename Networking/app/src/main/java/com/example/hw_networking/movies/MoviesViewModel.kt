package com.example.hw_networking.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoviesViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val moviesListLiveData = MutableLiveData<List<RemoteMovie>>()

    val movies: LiveData<List<RemoteMovie>>
        get() = moviesListLiveData

    fun search() {

    }
}