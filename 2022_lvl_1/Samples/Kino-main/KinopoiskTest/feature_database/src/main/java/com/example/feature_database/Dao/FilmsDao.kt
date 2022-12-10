package com.example.feature_database.Dao

import androidx.room.*
import com.example.feature_database.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilm(film: FilmEntity):Long

    @Delete
    fun deleteFilm(film: FilmEntity)

    @Update
    fun updateFilm(film: FilmEntity)

    @Query("SELECT*FROM films WHERE film_id =:filmId")
    fun getFilmById(filmId: Int):Flow<FilmEntity?>

    @Query("SELECT * FROM films WHERE film_id IN(:filmId)")
    @MapInfo(keyColumn = "film_id", valueColumn = "isLook")
    fun getFilmStatusByListId(filmId:List <Int>):Flow<Map<Int,Boolean?>>

    @Query("SELECT*FROM films")
    fun getAllFilms(): List<FilmEntity>
}