package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EmployeeProjectContract.TABLE_NAME)
data class EmployeeProject(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmployeeProjectContract.Columns.ID)
    val id: Long,

    @ColumnInfo(name = EmployeeProjectContract.Columns.EMPLOYEE_ID)
    val employeeId: Long,

    @ColumnInfo(name = EmployeeProjectContract.Columns.PROJECT_ID)
    val projectId: Long
)