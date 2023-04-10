package com.becker.beckerSkillCinema.data.localData.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.becker.beckerSkillCinema.data.localData.entities.Favorites
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorites(vararg favorites: Favorites)

    @Query("SELECT * FROM Favorites ORDER BY dateAdded DESC")
    fun getAllFavorites(): Flow<List<Favorites>>

    @Query("DELETE FROM Favorites WHERE favoritesId = :movieId")
    suspend fun deleteFromFavorites(movieId: Int)


}