package com.skillbox.m19_location.entity

import com.skillbox.m19_location.data.models.GeometryModel
import com.skillbox.m19_location.data.models.PropertiesModel

interface Attractions {
    val type: String
    val id: Long
    val geometryModel: GeometryModel
    val properties: PropertiesModel
}