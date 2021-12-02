package com.skillbox.lessons18_PermissionsAndDate

import org.threeten.bp.Instant

data class Message(
    val id: Long,
    val text: String,
    val createdAt: Instant
)