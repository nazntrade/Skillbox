package com.skillbox.hw_multithreading.threading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThreadingViewModel : ViewModel() {

    private val movieRepository = ThreadingRepository()
    private val movieIds = movieRepository.movieIds

    val movieFromViewModel
        get() = movieRepository.movieFromRepository

    private val showToastLiveData = SingleLiveEvent<Unit>()
    val showToastGet: LiveData<Unit>
        get() = showToastLiveData

    private val liveData = MutableLiveData<String>()
    val movies: LiveData<String?>
        get() = liveData

    fun getMovie() {
        movieRepository.fetchMovie(movieIds) { movies ->
            liveData.postValue(movies.toString())
            showToastLiveData.postValue(Unit)
        }
    }
}