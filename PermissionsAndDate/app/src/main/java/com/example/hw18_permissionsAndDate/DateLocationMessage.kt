package com.example.hw18_permissionsAndDate

import org.threeten.bp.Instant

data class DateLocationMessage(
        val id: Long,
        val image: String,
        var location: String,
        var createdAt: Instant
)
