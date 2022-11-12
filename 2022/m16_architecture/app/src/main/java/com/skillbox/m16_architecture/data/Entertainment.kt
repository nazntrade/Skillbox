package com.skillbox.m16_architecture.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Entertainment(
    @Json(name = "activity")
    val activity: String
)
