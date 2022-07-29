package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// here
@Entity(tableName = ProjectEmployeeCrossRefContract.TABLE_NAME)
data class ProjectEmployeeCrossRef(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectEmployeeCrossRefContract.Columns.ID)
    val id: Long,

    @ColumnInfo(name = ProjectEmployeeCrossRefContract.Columns.EMPLOYEE_ID)
    val employeeId: Long,

    @ColumnInfo(name = ProjectEmployeeCrossRefContract.Columns.PROJECT_ID)
    val projectId: Long
)