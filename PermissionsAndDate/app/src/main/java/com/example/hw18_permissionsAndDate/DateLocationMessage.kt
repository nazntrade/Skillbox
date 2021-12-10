package com.example.hw18_permissionsAndDate

import org.threeten.bp.LocalDateTime

data class DateLocationMessage(
        val id: Long,
        val image: String,
        var location: String,
        val createdAt: LocalDateTime
)
