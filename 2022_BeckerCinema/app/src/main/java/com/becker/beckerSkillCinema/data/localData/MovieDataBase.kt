package com.becker.beckerSkillCinema.data.localData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.becker.beckerSkillCinema.data.localData.dao.*
import com.becker.beckerSkillCinema.data.localData.entities.*

@Database(
    entities = [
        Favorites::class,
        ToWatch::class,
        Watched::class,
        Movie::class,
        CustomCollection::class,
        Interesting::class],
    version = MovieDataBase.DB_VERSION
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun toWatchDao(): ToWatchDao
    abstract fun watchedDao(): WatchedDao
    abstract fun customCollectionDao(): CustomCollectionDao
    abstract fun interestingDao(): InterestingDao

    companion object {
        const val DB_VERSION = 1
    }
}