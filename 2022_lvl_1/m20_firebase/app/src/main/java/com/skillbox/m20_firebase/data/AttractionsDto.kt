package com.skillbox.m20_firebase.data

import com.skillbox.m20_firebase.data.models.GeometryModel
import com.skillbox.m20_firebase.data.models.PropertiesModel
import com.skillbox.m20_firebase.entity.Attractions

//Dbo for dataBase
//Dto Data Transfer Object. For convert Interface and transfer deserialization Gson
data class AttractionsDto(
    override val type: String,
    override val id: Long,
    override val geometryModel: GeometryModel,
    override val properties: PropertiesModel
) : Attractions // Interface