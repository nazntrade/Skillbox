package com.example.hw_roomdao.data.db.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.hw_roomdao.data.db.models.Project

data class EmployeeWithProject(
    @Embedded val projectWithEmployee: ProjectWithEmployee,
    @Relation(
        parentColumn = "employeeName",
        entityColumn = "projectName",
        associateBy = Junction(ProjectEmployeeCrossRef::class)
    )
    val projects: List<Project>
)
