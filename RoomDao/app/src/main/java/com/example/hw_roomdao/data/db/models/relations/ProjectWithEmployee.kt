package com.example.hw_roomdao.data.db.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.ProjectContract

data class ProjectWithEmployee(
    @Embedded val project: Project,
    @Relation(
        parentColumn = ProjectContract.Columns.ID,
        entityColumn = EmployeeContract.Columns.ID,
        associateBy = Junction(ProjectEmployeeCrossRef::class)
    )
    val employees: List<Employee>
)
