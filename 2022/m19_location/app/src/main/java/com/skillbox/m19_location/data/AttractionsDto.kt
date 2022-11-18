package com.skillbox.m19_location.data

import com.skillbox.m19_location.entity.Attractions
//Dbo for dataBase
//Dto Data Transfer Object. For convert Interface and transfer deserialization Gson
class AttractionsDto(
    override val activity: String,
    override val type: String,
    override val participants: Int,
    override val price: Float,
    override val link: String,
    override val key: Long,
    override val accessibility: Float
) : Attractions // Interface