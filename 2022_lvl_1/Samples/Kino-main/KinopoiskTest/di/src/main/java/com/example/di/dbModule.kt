package com.example.di

import androidx.room.Room
import com.example.feature_database.CinemaDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataBaseModule = module {
    single(named("database")) {
        Room.databaseBuilder(
            get(),
            CinemaDatabase::class.java,
            "StoreDataBase"
        ).build()
    }

    single {
        get<CinemaDatabase>(named("database")).genreDao()
    }

    single {
        get<CinemaDatabase>(named("database")).countryDao()
    }

    single {
        get<CinemaDatabase>(named("database")).filmsDao()
    }

    single {
        get<CinemaDatabase>(named("database")).filmsCollectionDao()
    }
}