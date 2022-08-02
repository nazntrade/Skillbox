package com.example.hw_roomdao.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract
import com.example.hw_roomdao.data.db.models.ProjectContract
import com.example.hw_roomdao.data.db.models.relations.EmployeeWithProject
import com.example.hw_roomdao.data.db.models.relations.ProjectWithEmployee

@Dao
interface RelationsDao {

    @Transaction
    @Query("SELECT * FROM ${ProjectContract.TABLE_NAME}")
    fun getProjectWithEmployee(): List<ProjectWithEmployee>

    @Transaction
    @Query("SELECT * FROM ${ProjectContract.TABLE_NAME} WHERE projectId = :projectId")
    fun getProjectWithEmployeeById(projectId: Long): ProjectWithEmployee

    @Transaction
    @Query("SELECT * FROM ${EmployeeContract.TABLE_NAME}")
    fun getEmployeeWithProject(): List<EmployeeWithProject>

    @Query("DELETE FROM ${ProjectContract.TABLE_NAME} WHERE projectId = :projectId")
    fun removeEmployeeInCurrentProjectById(projectId: Long)


}