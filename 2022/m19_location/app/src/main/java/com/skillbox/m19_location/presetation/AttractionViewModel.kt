package com.skillbox.m19_location.presetation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m19_location.domain.GetAttractionsUseCase
import com.skillbox.m19_location.entity.Attractions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AttractionViewModel @Inject constructor(
    private val getAttractionsUseCase: GetAttractionsUseCase
) : ViewModel() {

    private val _attractionsFlow = MutableStateFlow<List<Attractions>?>(null)
    val attractionsFlow = _attractionsFlow.asStateFlow()

    fun getAttractions(lon: Double, lat: Double) {
        viewModelScope.launch {
            try {
                val attractions = getAttractionsUseCase.execute(lon, lat)
                _attractionsFlow.value = attractions
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}