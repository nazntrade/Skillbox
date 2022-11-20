package com.skillbox.m19_location.data.models

import android.media.MicrophoneInfo.Coordinate3F
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class GeometryModel(
    val type: String,
    val coordinates: List<Double>,

)
