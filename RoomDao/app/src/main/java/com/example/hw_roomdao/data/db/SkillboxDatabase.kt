package com.example.hw_roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeProject
import com.example.hw_roomdao.data.db.models.Messages

@Database(
    entities = [
        Employee::class,
        EmployeeProject::class,
        Project::class,
        Messages::class
    ], version = SkillboxDatabase.DB_VERSION
)
abstract class SkillboxDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun employeeDao(): EmployeeDao
//    abstract fun messageDao(): MessageDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "skillbox-database"
    }
}