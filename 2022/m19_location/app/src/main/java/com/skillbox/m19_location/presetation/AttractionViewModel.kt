package com.skillbox.m19_location.presetation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m19_location.domain.GetAttractionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AttractionViewModel @Inject constructor(
    private val getAttractionsUseCase: GetAttractionsUseCase
) : ViewModel() {

    private val _usefulActivityTextFlow = MutableStateFlow("")
    val usefulActivityTextFlow = _usefulActivityTextFlow.asStateFlow()

    fun getUsefulActivity() {
        viewModelScope.launch {
            try {
                val someDoing = getAttractionsUseCase.execute()
                _usefulActivityTextFlow.value = someDoing.activity
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}