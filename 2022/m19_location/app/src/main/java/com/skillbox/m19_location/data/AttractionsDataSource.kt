package com.skillbox.m19_location.data

import com.skillbox.m19_location.data.network.Networking
import javax.inject.Inject

class AttractionsDataSource @Inject constructor() {
    suspend fun loadActivity(): AttractionsDto {
        val dataFromApi = Networking.attractionsApi.getAttractions()
        val activityFromApi = dataFromApi.some
        val typeFromApi = dataFromApi.type
        val participantsFromApi = dataFromApi.participants
        val priceFromApi = dataFromApi.price
        val linkFromApi = dataFromApi.link
        val keyFromApi = dataFromApi.key
        val accessibilityFromApi = dataFromApi.accessibility
        return AttractionsDto(
            activity = activityFromApi,
            type = typeFromApi,
            participants = participantsFromApi,
            price = priceFromApi,
            link = linkFromApi,
            key = keyFromApi,
            accessibility = accessibilityFromApi
        )
    }
}