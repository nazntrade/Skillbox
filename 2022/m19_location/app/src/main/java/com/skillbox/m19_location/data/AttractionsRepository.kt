package com.skillbox.m19_location.data

import javax.inject.Inject

class AttractionsRepository @Inject constructor(
    private val attractionsDataSource: AttractionsDataSource
) {
    suspend fun getAttractions(): List<AttractionsDto> {
        return attractionsDataSource.loadAttractions()
    }
}