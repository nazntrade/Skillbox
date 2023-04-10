package com.becker.beckerSkillCinema.data.di

import android.app.Application
import androidx.room.Room
import com.becker.beckerSkillCinema.data.localData.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object Module {

    @Provides
    @ViewModelScoped
    fun provideMovieDatabase(app: Application): MovieDataBase {
        return Room.databaseBuilder(
            app.applicationContext,
            MovieDataBase::class.java,
            "movieDataBase"
        ).build()
    }

    @Provides
    @ViewModelScoped
    fun provideFavoritesDao(dataBase: MovieDataBase) = dataBase.favoritesDao()

    @Provides
    @ViewModelScoped
    fun provideToWatchDao(dataBase: MovieDataBase) = dataBase.toWatchDao()

    @Provides
    @ViewModelScoped
    fun provideWatchedDao(dataBase: MovieDataBase) = dataBase.watchedDao()

    @Provides
    @ViewModelScoped
    fun provideMovieDao(dataBase: MovieDataBase) = dataBase.movieDao()

    @Provides
    @ViewModelScoped
    fun provideInterestingDao(dataBase: MovieDataBase) = dataBase.interestingDao()

    @Provides
    @ViewModelScoped
    fun provideCustomSelectionDao(dataBase: MovieDataBase) = dataBase.customCollectionDao()
}