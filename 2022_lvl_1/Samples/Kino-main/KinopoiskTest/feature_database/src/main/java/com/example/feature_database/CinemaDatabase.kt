package com.example.feature_database

import androidx.room.*
import androidx.room.Database
import com.example.core.tools.general.Country
import com.example.core.tools.general.Genre
import com.example.feature_database.Dao.*
import com.example.feature_database.entity.FilmEntity
import com.example.feature_database.entity.FilmsCollectionEntity
import com.example.feature_database.entity.MergeCollectionAndFilms

@Database(
    entities = [
        Genre::class,
        Country::class,
        FilmEntity::class,
        MergeCollectionAndFilms::class,
        FilmsCollectionEntity::class
    ], version = 1
)
@TypeConverters(Converter::class)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun genreDao(): GenreDao

    abstract fun filmsDao(): FilmsDao

    abstract fun filmsCollectionDao():FilmsCollectionDao
}