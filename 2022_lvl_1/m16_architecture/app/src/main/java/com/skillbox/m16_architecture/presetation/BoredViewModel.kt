package com.skillbox.m16_architecture.presetation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m16_architecture.domain.GetUsefulActivityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BoredViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {

    private val _usefulActivityTextFlow = MutableStateFlow("")
    val usefulActivityTextFlow = _usefulActivityTextFlow.asStateFlow()

    fun getUsefulActivity() {
        viewModelScope.launch {
            try {
                val someDoing = getUsefulActivityUseCase.execute()
                _usefulActivityTextFlow.value = someDoing.activity
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}