package com.skillbox.m19_location.domain

import com.skillbox.m19_location.data.AttractionsDto
import com.skillbox.m19_location.data.AttractionsRepository
import com.skillbox.m19_location.entity.Attractions
import javax.inject.Inject

class GetAttractionsUseCase @Inject constructor(
    private val attractionsRepository: AttractionsRepository
) {
    suspend fun execute(): List<AttractionsDto> {
        return attractionsRepository.getAttractions()
    }
}