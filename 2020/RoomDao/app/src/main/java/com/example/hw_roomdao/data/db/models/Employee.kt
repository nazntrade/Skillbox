package com.example.hw_roomdao.data.db.models

import androidx.room.*
import com.example.hw_roomdao.data.db.EmployeePositionConverter

@Entity(
    tableName = EmployeeContract.TABLE_NAME,
    indices = [Index(EmployeeContract.Columns.LAST_NAME)] // in order to search FASTER
)
@TypeConverters(EmployeePositionConverter::class)
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
    @ColumnInfo(name = EmployeeContract.Columns.POSITION)
    val position: EmployeePosition,
    @ColumnInfo(name = EmployeeContract.Columns.AGE, defaultValue = "0")
    val age: Int
)