package com.skillbox.m19_location.data.models

import com.google.gson.annotations.SerializedName

data class PropertiesModel(
    @SerializedName("xid")
    val xid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dist")
    val dist: Float,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("kinds")
    val kinds: String
)
