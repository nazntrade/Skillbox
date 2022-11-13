package com.skillbox.m16_architecture.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsefulActivity(
    @Json(name = "activity")
    val activity: String

)
