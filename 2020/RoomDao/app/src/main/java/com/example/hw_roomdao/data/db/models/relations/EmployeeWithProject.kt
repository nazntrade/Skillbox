package com.example.hw_roomdao.data.db.models.relations

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.ProjectContract

data class EmployeeWithProject(
    @Embedded val employee: Employee,

    @Relation(
        parentColumn = EmployeeContract.Columns.ID,
        entityColumn = ProjectContract.Columns.ID,
        associateBy = Junction(ProjectEmployeeCrossRef::class)
    )
    val projects: List<Project>
)