package com.skillbox.m19_location.data.models

import com.google.gson.annotations.SerializedName

data class CoordinatesModel(
    @SerializedName("lon")
    val lon: Float,
    @SerializedName("lat")
    val lat: Float
)
