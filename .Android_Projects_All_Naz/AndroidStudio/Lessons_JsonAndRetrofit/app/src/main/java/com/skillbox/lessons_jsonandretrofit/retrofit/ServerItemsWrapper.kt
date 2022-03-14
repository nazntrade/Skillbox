package com.skillbox.lessons_jsonandretrofit.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemsWrapper<T>(
    val items: List<T>
)