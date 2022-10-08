package com.skillbox.permissions_date_14

import org.threeten.bp.Instant

data class Message(
    val id: Long,
    val text: String,
    val createdAt: Instant
)