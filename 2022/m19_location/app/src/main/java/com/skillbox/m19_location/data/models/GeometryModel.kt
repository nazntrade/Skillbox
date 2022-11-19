package com.skillbox.m19_location.data.models

import android.media.MicrophoneInfo.Coordinate3F
import com.google.gson.annotations.SerializedName


data class GeometryModel(
    @SerializedName("type")
    val type: String,
    @SerializedName("coordinates")
    val coordinates: CoordinatesModel,

)
