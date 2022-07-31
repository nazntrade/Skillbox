package com.example.hw_roomdao.data.db.models.relations

import androidx.room.Entity

@Entity(primaryKeys = ["projectId", "employeeId"])
data class ProjectEmployeeCrossRef(
    val projectId: Long,
    val employeeId: Long
)