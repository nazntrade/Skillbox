package com.skillbox.hw_multithreading.threading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.hw_multithreading.networking.Movie

class ThreadingViewModel : ViewModel() {

    private val movieRepository = ThreadingRepository()
    private val movieIds = movieRepository.movieIds

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = moviesLiveData

    fun fetchMovie() {
        movieRepository.fetchMovie(movieIds) { movies ->
            moviesLiveData.postValue(movies)
        }
    }
}