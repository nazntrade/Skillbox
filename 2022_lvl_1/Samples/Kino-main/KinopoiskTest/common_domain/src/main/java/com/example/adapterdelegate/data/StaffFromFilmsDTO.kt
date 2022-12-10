package com.example.adapterdelegate.data

import com.example.core.tools.GRID_ACTOR_SIZE
import com.example.core.tools.GRID_STAFF_SIZE
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.DetailsCategory

class StaffFromFilmsDTO(
    val description: String?,
    val nameRu: String,
    val posterUrl: String,
    val professionKey: String,
    val professionText: String,
    val staffId: Int
){
    fun toStaff() =
        StaffFromFilms(description, nameRu, posterUrl, professionText, staffId)
}

fun List<StaffFromFilmsDTO>.createStaffList(): List<BaseCategory> {
    val staffList = mutableListOf<NestedInfoInCategory>()
    val actorList = mutableListOf<NestedInfoInCategory>()
    forEach { dto ->
        if (dto.professionKey == "ACTOR") actorList.add(dto.toStaff())
        else staffList.add(dto.toStaff())
    }
    return listOf(
        DetailsCategory(CategoryInfo.ACTOR, actorList.take(GRID_ACTOR_SIZE)),
        DetailsCategory(CategoryInfo.STAFF, staffList.take(GRID_STAFF_SIZE))
    )
}