package com.skillbox.coroutines.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemsWrapper<T>(
    val items: List<T>
)
