package com.skillbox.m16_architecture.presetation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m16_architecture.data.BoredRepository
import com.skillbox.m16_architecture.data.Entertainment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class BoredViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = BoredRepository(app)

    private val _entertainmentStateFlow = MutableStateFlow<Entertainment?>(null)
    val entertainmentStateFlow = _entertainmentStateFlow.asStateFlow()

    fun getEntertainment() {
        viewModelScope.launch {
            try {
                _entertainmentStateFlow.value = repository.getEntertainment()
            } catch (t : Throwable) {
                Timber.e(t)
            }
        }
    }

}