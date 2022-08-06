package com.example.hw_roomdao.data.db

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Director
import com.example.hw_roomdao.data.db.models.DirectorContract
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract

@Dao
interface DirectorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDirector(director: Director)

    @Update
    fun updateDirector(director: Director)

    @Query("SELECT * FROM ${DirectorContract.TABLE_NAME}")
    fun getDirector(): Director

}