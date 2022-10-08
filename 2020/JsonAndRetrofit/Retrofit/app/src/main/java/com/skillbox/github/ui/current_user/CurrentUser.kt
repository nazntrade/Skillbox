package com.skillbox.github.ui.current_user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentUser(
    val id: Long,
    val login: String,
    val avatar_url: String?,
    val name: String,
    val location: String
)
