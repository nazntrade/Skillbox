package com.example.hw_roomdao.data.db

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.ProjectContract

@Dao
interface ProjectDao {
    @Insert
    fun insertProject(project: List<Project>)

    @Delete
    fun removeProject(project: Project)

    @Update
    fun updateProject(project: Project)

    @Query("SELECT * FROM ${ProjectContract.TABLE_NAME}")
    fun getAllProject(): List<Project>

}