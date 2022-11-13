package com.skillbox.m16_architecture.presetation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m16_architecture.data.BoredRepository
import com.skillbox.m16_architecture.data.entity.UsefulActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class BoredViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = BoredRepository(app)

    private val _usefulActivityStateFlow = MutableStateFlow<UsefulActivity?>(null)
    val usefulActivityStateFlow = _usefulActivityStateFlow.asStateFlow()

    fun getUsefulActivity() {
        viewModelScope.launch {
            try {
                _usefulActivityStateFlow.value = repository.getUsefulActivity()
            } catch (t : Throwable) {
                Timber.e(t)
            }
        }
    }
}