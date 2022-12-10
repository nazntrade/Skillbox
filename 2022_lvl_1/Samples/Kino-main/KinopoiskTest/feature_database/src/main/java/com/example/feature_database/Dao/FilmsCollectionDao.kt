package com.example.feature_database.Dao

import androidx.room.*
import com.example.feature_database.entity.FilmEntity
import com.example.feature_database.entity.FilmsCollectionEntity
import com.example.feature_database.entity.MergeCollectionAndFilms
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsCollectionDao {

    @Query("SELECT merge.film_id_collection  " +
            "FROM films_collections " +
            "INNER JOIN merge " +
            "ON id = collection_id " +
            "WHERE collection_id =:collectionID ")
    fun getFilmsFromCollectionID(collectionID: Int): List <Int>

    @Query("SELECT collection_id  " +
            "FROM films_collections " +
            "INNER JOIN merge " +
            "ON id = collection_id "+
        "WHERE film_id_collection =:filmID ")
    fun getCollectionsFromFilmID(filmID: Int?): List <Int>?

    @Query("SELECT collection_id  " +
            "FROM films_collections " +
            "INNER JOIN merge " +
            "ON id = collection_id "+
            "WHERE film_id_collection =:filmID AND id=:collectionId")
    fun getCollectionsFromFilmIDAndCollectionID(filmID: Int?,collectionId: Int): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMergeCollectionAndFilms(merge: MergeCollectionAndFilms):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCollection(collection: FilmsCollectionEntity):Long

    @Query("SELECT*FROM films_collections")
    fun getCollection(): Flow<List<FilmsCollectionEntity>>

    @Query("SELECT*FROM films_collections WHERE id=:collectionId")
    fun getCollectionEntity(collectionId: Int): FilmsCollectionEntity

    @Update
    fun updateCollection(collection: FilmsCollectionEntity):Int

    @Query("SELECT * FROM films WHERE film_id IN(:filmId)")
    fun getFilmsByListId(filmId:List <Int>):List<FilmEntity?>

    @Delete
    fun deleteFilm(mergeCollectionAndFilms: MergeCollectionAndFilms)

}