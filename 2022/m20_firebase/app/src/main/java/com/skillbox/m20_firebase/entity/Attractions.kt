package com.skillbox.m20_firebase.entity

import com.skillbox.m20_firebase.data.models.GeometryModel
import com.skillbox.m20_firebase.data.models.PropertiesModel

interface Attractions {
    val type: String
    val id: Long
    val geometryModel: GeometryModel
    val properties: PropertiesModel
}