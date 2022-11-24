package com.skillbox.m19_location.domain

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.skillbox.m19_location.data.CurrentCoordinatesRepository
import com.skillbox.m19_location.entity.Attractions
import javax.inject.Inject

class GetMapUseCase @Inject constructor(
    private val mapRepository: CurrentCoordinatesRepository
) {
    suspend fun execute(myCoordinates: LatLng, attractions: List<Attractions>): OnMapReadyCallback {
        return mapRepository.getMap(myCoordinates, attractions)
    }
}