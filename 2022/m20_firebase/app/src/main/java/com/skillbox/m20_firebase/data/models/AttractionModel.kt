package com.skillbox.m20_firebase.data.models

import com.google.gson.annotations.SerializedName

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