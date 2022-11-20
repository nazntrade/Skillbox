package com.skillbox.m19_location.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

data class AttractionModel(
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("geometry")
    val geometry: GeometryModel,
    @SerializedName("properties")
    val properties: PropertiesModel
)