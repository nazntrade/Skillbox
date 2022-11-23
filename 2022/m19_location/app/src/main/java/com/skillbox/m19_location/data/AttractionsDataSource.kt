package com.skillbox.m19_location.data

import com.google.android.gms.common.api.internal.ApiKey
import com.skillbox.m19_location.data.network.Networking
import com.skillbox.m19_location.utils.Constants
import javax.inject.Inject

class AttractionsDataSource @Inject constructor() {
    suspend fun loadAttractions(radius: Int, lon: Double, lat: Double, apiKey: String): List<AttractionsDto> {
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