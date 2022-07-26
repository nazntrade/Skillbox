package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hw_roomdao.R

@Entity(tableName = EmployeeContract.TABLE_NAME)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmployeeContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EmployeeContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = EmployeeContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = EmployeeContract.Columns.EMAIL)
    val email: String,
)