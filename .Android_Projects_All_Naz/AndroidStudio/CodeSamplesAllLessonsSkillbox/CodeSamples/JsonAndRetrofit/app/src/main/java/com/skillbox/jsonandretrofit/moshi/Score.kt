package com.skillbox.jsonandretrofit.moshi

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Score(
    val source: String,
    val value: String
)