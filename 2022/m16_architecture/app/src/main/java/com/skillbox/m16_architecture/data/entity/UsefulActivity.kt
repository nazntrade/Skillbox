package com.skillbox.m16_architecture.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
interface UsefulActivity{
    @Json(name = "activity")
    val activity: String
}