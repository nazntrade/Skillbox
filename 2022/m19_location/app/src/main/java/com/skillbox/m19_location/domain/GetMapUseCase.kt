package com.skillbox.m19_location.domain

import com.google.android.gms.maps.OnMapReadyCallback
import com.skillbox.m19_location.data.CurrentCoordinatesRepository
import javax.inject.Inject

class GetMapUseCase @Inject constructor(
    private val mapRepository: CurrentCoordinatesRepository
) {
    suspend fun execute(myLat: Double, myLon: Double): OnMapReadyCallback {
        return mapRepository.getMap(myLat, myLon)
    }
}