package com.example.feature_database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.tools.general.Genre

@Dao
interface GenreDao{

    @Insert
    fun insertGenre(genre:List<Genre>)

    @Query("SELECT*FROM genre WHERE info =:name")
    fun getGenreByName(name:String): Genre?


    @Query("SELECT*FROM genre")
    fun getAllGenre():List<Genre>

    @Query("SELECT info FROM genre WHERE id = :genreId")
    fun getFromGenreID(genreId: Int):String
}