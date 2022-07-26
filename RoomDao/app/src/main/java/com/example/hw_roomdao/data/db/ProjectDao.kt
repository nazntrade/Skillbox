package com.example.hw_roomdao.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.ProjectContract

@Dao
interface ProjectDao {
    @Insert
    fun insertProject(project: List<Project>)

    @Delete
    fun removeProject(project: Project)

    @Query("SELECT * FROM ${ProjectContract.TABLE_NAME}")
    fun getAllProject(): List<Project>

}