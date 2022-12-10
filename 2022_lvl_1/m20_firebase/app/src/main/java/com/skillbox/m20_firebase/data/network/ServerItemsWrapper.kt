package com.skillbox.m20_firebase.data.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemsWrapper<T>(
    @SerializedName("features")
    val features: List<T>
)
