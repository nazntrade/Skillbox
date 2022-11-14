package com.skillbox.m16_architecture.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsefulActivity (
    @Json(name = "activity")
    val activity: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "participants")
    val participants: Int,
    @Json(name = "price")
    val price: Float,
    @Json(name = "link")
    val link: String,
    @Json(name = "key")
    val key: Long,
    @Json(name = "accessibility")
    val accessibility: Float
)
