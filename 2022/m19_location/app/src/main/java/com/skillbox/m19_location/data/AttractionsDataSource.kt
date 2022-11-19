package com.skillbox.m19_location.data

import com.skillbox.m19_location.data.network.Networking
import com.skillbox.m19_location.utils.Constants
import javax.inject.Inject

class AttractionsDataSource @Inject constructor() {
    suspend fun loadActivity(): List<AttractionsDto> {
        val attractionsDtoList = listOf<AttractionsDto>()
        val dataFromApi =
            Networking.attractionsApi.getAttractions(-79.0538864, 43.0974998, Constants.API_KEY)

        dataFromApi.forEach {
            val attraction = AttractionsDto(
                it.type,
                it.id,
                it.geometry,
                it.properties
            )
            attractionsDtoList + attraction
        }

        return attractionsDtoList
    }
}