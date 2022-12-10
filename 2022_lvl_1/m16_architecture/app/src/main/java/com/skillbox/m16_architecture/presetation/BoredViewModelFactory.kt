package com.skillbox.m16_architecture.presetation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class BoredViewModelFactory @Inject constructor(private val boredViewModel: BoredViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoredViewModel::class.java)) {
            return boredViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}