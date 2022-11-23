package com.skillbox.m19_location.data

import com.google.android.gms.maps.OnMapReadyCallback
import javax.inject.Inject

class CurrentCoordinatesRepository @Inject constructor(
    private val mapDataSource: MapDataSource
) {
    suspend fun getMap(myLat: Double, myLon: Double): OnMapReadyCallback {
        return mapDataSource.loadMap(myLat, myLon)
    }

}