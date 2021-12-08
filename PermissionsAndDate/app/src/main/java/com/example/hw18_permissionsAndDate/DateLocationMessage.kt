package com.example.hw18_permissionsAndDate

import org.threeten.bp.Instant

data class DateLocationMessage(
        val id: Long,
        val image: String,
        val location: String,
        val createAt: Instant
)
