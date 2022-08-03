package com.example.hw_roomdao.data.db.models.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = ProjectEmployeeCrossRefContract.TABLE_NAME,
    primaryKeys = [ProjectEmployeeCrossRefContract.Columns.PROJECT_ID,
        ProjectEmployeeCrossRefContract.Columns.EMPLOYEE_ID]
)
data class ProjectEmployeeCrossRef(
    @ColumnInfo(name = ProjectEmployeeCrossRefContract.Columns.PROJECT_ID)
    val projectId: Long,
    @ColumnInfo(name = ProjectEmployeeCrossRefContract.Columns.EMPLOYEE_ID)
    val employeeId: Long
)