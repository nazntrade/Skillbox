package com.skillbox.m19_location.data.models

import com.google.gson.annotations.SerializedName

data class GeometryModel(
    @SerializedName("type")
    val type: String,
    @SerializedName("coordinates")
    val coordinates: List<Double>,

    )
