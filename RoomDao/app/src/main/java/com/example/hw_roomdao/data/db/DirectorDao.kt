package com.example.hw_roomdao.data.db

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Director

@Dao
interface DirectorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDirector(director: Director)

    @Update
    fun updateDirector(director: Director)

}