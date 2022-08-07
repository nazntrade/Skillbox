package com.example.hw_roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.Director
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.relations.ProjectEmployeeCrossRef

@Database(
    entities = [
        Company::class,
        Director::class,
        Project::class,
        Employee::class,
        ProjectEmployeeCrossRef::class,
//        Messages::class
    ], version = SkillboxDatabase.DB_VERSION
)
abstract class SkillboxDatabase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao
    abstract fun directorDao(): DirectorDao
    abstract fun projectDao(): ProjectDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun relationsDao(): RelationsDao
//    abstract fun messageDao(): MessageDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "skillbox-database"
    }
}

//import com.example.hw_roomdao.data.db.models.Messages
