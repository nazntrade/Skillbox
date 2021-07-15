package com.example.generics_01

sealed class Result<out T, R>(val successfulResult: T? = null, val mistake: R? = null) {
    data class Success<T, R>(val info: T) : Result<T, R>(successfulResult = info)
    data class Error<T, R>(val info: R) : Result<T, R>(mistake = info)
}
