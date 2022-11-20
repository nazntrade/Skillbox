package com.skillbox.m19_location.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class PropertiesModel(
    val xid: String,
    val name: String,
    val dist: Float,
    val rate: Int,
    val wikidata: String,
    val kinds: String
)
