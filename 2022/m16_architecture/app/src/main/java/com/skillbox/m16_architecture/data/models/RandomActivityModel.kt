package com.skillbox.m16_architecture.data.models

import com.google.gson.annotations.SerializedName

data class RandomActivityModel(
    @SerializedName("activity")
    val activity: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("participants")
    val participants: Int,
    @SerializedName("price")
    val price: Float,
    @SerializedName("link")
    val link: String,
    @SerializedName("key")
    val key: Long,
    @SerializedName("accessibility")
    val accessibility: Float
)