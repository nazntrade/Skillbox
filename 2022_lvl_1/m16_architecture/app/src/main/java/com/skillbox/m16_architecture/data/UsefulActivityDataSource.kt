package com.skillbox.m16_architecture.data

import com.skillbox.m16_architecture.data.network.Networking
import javax.inject.Inject

class UsefulActivityDataSource @Inject constructor() {
    suspend fun loadActivity(): UsefulActivityDto {
        val dataFromApi = Networking.boredApi.getUsefulActivity()
        val activityFromApi = dataFromApi.activity
        val typeFromApi = dataFromApi.type
        val participantsFromApi = dataFromApi.participants
        val priceFromApi = dataFromApi.price
        val linkFromApi = dataFromApi.link
        val keyFromApi = dataFromApi.key
        val accessibilityFromApi = dataFromApi.accessibility
        return UsefulActivityDto(
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