package com.skillbox.m20_firebase.data

import com.skillbox.m20_firebase.data.network.Networking
import javax.inject.Inject

class AttractionsDataSource @Inject constructor() {
    suspend fun loadAttractions(
        radius: Int,
        lon: Double,
        lat: Double,
        apiKey: String
    ): List<AttractionsDto> {
        val attractionsDtoList = mutableListOf<AttractionsDto>()
        val dataFromApi =
            Networking.attractionsApi.getAttractions(
                radius,
                lon,
                lat,
                apiKey
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