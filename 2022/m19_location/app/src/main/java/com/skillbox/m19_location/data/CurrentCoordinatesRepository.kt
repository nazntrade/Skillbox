package com.skillbox.m19_location.data

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.skillbox.m19_location.entity.Attractions
import javax.inject.Inject

class CurrentCoordinatesRepository @Inject constructor(
    private val mapDataSource: MapDataSource
) {
    suspend fun getMap(myCoordinates: LatLng, attractions: List<Attractions>): OnMapReadyCallback {
        return mapDataSource.loadMap(myCoordinates, attractions)
    }

}