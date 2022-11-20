package com.skillbox.m19_location.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttractionModel(
    val type: String,
    val id: Long,
    val geometry: GeometryModel,
    val properties: PropertiesModel
)