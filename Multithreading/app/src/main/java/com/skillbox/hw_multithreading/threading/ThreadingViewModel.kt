package com.skillbox.hw_multithreading.threading

import androidx.lifecycle.ViewModel

class ThreadingViewModel : ViewModel() {

    private val movieRepository = ThreadingRepository()
    private val movieIds = movieRepository.movieIds

    fun getMovie() {
        movieRepository.fetchMovie(movieIds)
    }

}