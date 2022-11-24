package com.skillbox.m19_location.presetation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.skillbox.m19_location.domain.GetAttractionsUseCase
import com.skillbox.m19_location.domain.GetMapUseCase
import com.skillbox.m19_location.entity.Attractions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AttractionViewModel @Inject constructor(
    private val getAttractionsUseCase: GetAttractionsUseCase,
    private val getMapUseCase: GetMapUseCase,
) : ViewModel() {

    private val _attractionsFlow = MutableStateFlow<List<Attractions>?>(null)
    val attractionsFlow = _attractionsFlow.asStateFlow()

    private val _mapFlow = MutableStateFlow<OnMapReadyCallback?>(null)
    val mapFlow = _mapFlow.asStateFlow()

    fun getMap(myCoordinates: LatLng, attractions: List<Attractions>) {
        viewModelScope.launch {
            try {
                _mapFlow.value = getMapUseCase.execute(myCoordinates, attractions)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun getAttractions(radius: Int, lon: Double, lat: Double) {
        viewModelScope.launch {
            try {
                val attractions = getAttractionsUseCase.execute(radius, lon, lat)
                _attractionsFlow.value = attractions
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}