package com.skillbox.m19_location.data

import com.skillbox.m19_location.entity.Attractions
import javax.inject.Inject

class AttractionsRepository @Inject constructor(
    private val attractionsDataSource: AttractionsDataSource
) {
    suspend fun getAttractions(): Attractions {
        return attractionsDataSource.loadActivity()
    }
}