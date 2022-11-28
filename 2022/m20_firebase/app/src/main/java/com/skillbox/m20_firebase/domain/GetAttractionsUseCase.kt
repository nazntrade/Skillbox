package com.skillbox.m20_firebase.domain

import com.skillbox.m20_firebase.data.AttractionsDto
import com.skillbox.m20_firebase.data.AttractionsRepository
import javax.inject.Inject

class GetAttractionsUseCase @Inject constructor(
    private val attractionsRepository: AttractionsRepository
) {
    suspend fun execute(lon: Double, lat: Double): List<AttractionsDto> {
        return attractionsRepository.getAttractions(lon, lat)
    }
}