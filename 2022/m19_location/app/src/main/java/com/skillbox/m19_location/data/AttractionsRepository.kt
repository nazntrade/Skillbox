package com.skillbox.m19_location.data

import com.skillbox.m19_location.utils.Constants
import javax.inject.Inject

class AttractionsRepository @Inject constructor(
    private val attractionsDataSource: AttractionsDataSource
) {
    suspend fun getAttractions(lon: Double, lat: Double): List<AttractionsDto> {
        val radius = 1000
        return attractionsDataSource.loadAttractions(radius, lon, lat, Constants.API_KEY)
    }
}