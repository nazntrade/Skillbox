package com.skillbox.m19_location.domain

import com.skillbox.m19_location.data.AttractionsDto
import com.skillbox.m19_location.data.AttractionsRepository
import javax.inject.Inject

class GetAttractionsUseCase @Inject constructor(
    private val attractionsRepository: AttractionsRepository
) {
    suspend fun execute(lon: Double, lat: Double): List<AttractionsDto> {
        return attractionsRepository.getAttractions(lon, lat)
    }
}