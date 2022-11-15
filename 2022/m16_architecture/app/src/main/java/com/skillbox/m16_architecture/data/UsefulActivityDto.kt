package com.skillbox.m16_architecture.data

import com.skillbox.m16_architecture.entity.UsefulActivity

class UsefulActivityDto(
    override val activity: String,
    override val type: String,
    override val participants: Int,
    override val price: Float,
    override val link: String,
    override val key: Long,
    override val accessibility: Float
) : UsefulActivity