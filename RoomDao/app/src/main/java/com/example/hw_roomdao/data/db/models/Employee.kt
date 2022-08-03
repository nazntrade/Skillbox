package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EmployeeContract.TABLE_NAME)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmployeeContract.Columns.ID)
    val employeeId: Long,
    @ColumnInfo(name = EmployeeContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = EmployeeContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = EmployeeContract.Columns.EMAIL)
    val email: String,
)