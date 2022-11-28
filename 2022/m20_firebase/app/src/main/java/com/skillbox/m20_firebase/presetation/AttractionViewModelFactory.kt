package com.skillbox.m20_firebase.presetation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AttractionViewModelFactory @Inject constructor(private val attractionViewModel: AttractionViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttractionViewModel::class.java)) {
            return attractionViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}