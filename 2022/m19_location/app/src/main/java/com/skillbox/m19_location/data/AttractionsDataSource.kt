package com.skillbox.m19_location.data

import com.skillbox.m19_location.data.network.Networking
import com.skillbox.m19_location.utils.Constants
import javax.inject.Inject

class AttractionsDataSource @Inject constructor() {
    suspend fun loadAttractions(): List<AttractionsDto> {
        val attractionsDtoList = mutableListOf<AttractionsDto>()
        val dataFromApi =
            Networking.attractionsApi.getAttractions(
                1000,
                -79.0538864,
                43.0974998,
                Constants.API_KEY
            )

        dataFromApi.features.forEach { attractionModel ->
            attractionsDtoList.add(
                AttractionsDto(
                    type = attractionModel.type,
                    id = attractionModel.id,
                    geometryModel = attractionModel.geometry,
                    properties = attractionModel.properties
                )
            )
        }

        return attractionsDtoList
    }
}