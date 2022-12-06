package com.skillbox.m20_firebase.data

import com.skillbox.m20_firebase.utils.Constants
import javax.inject.Inject

class AttractionsRepository @Inject constructor(
    private val attractionsDataSource: AttractionsDataSource
) {
    suspend fun getAttractions(lon: Double, lat: Double): List<AttractionsDto> {
        val radius = 2000
        return attractionsDataSource.loadAttractions(
            radius,
            lon,
            lat,
            Constants.OPEN_TRIP_MAP_1 + Constants.OPEN_TRIP_MAP_2 + Constants.OPEN_TRIP_MAP_3
        )
    }
}