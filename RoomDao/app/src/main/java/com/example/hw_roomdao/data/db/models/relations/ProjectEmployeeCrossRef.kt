package com.example.hw_roomdao.data.db.models.relations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.ProjectContract

@Entity(primaryKeys = ["projectName", "employeeName"])
data class ProjectEmployeeCrossRef(
    val projectName: String,
    val employeeName: String
)