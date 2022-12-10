package com.example.feature_database.repository

import android.util.Log
import com.example.core.tools.ID_FAVORITE_COLLECTION
import com.example.core.tools.ID_HISTORY_COLLECTION
import com.example.core.tools.ID_LIKE_COLLECTION
import com.example.feature_database.Dao.FilmsCollectionDao
import com.example.feature_database.Dao.FilmsDao
import com.example.feature_database.entity.FilmEntity
import com.example.feature_database.entity.FilmsCollectionEntity
import com.example.feature_database.entity.MergeCollectionAndFilms

class CollectionsFilmsRepository(
    private val filmsDao: FilmsDao,
    private val filmsCollectionDao: FilmsCollectionDao
) {

    fun insetFilm(filmEntity: FilmEntity) = filmsDao.insertFilm(filmEntity)

    fun insertFilmCollection(filmEntity: FilmEntity, collectionId: Int) {

        val collectionEntity = filmsCollectionDao.getCollectionEntity(collectionId)
        collectionEntity.size++

        val isSuccess = filmsCollectionDao
            .insertMergeCollectionAndFilms(
                MergeCollectionAndFilms(collectionId, filmEntity.filmId)
            )
        Log.e("Kart", isSuccess.toString())
        if (isSuccess != -1L && collectionId != ID_HISTORY_COLLECTION)
            filmsCollectionDao.updateCollection(collectionEntity)
    }

    fun insertFromBottomShit(filmEntity: FilmEntity, collectionId: Int, isCheck: Boolean) {
        likeAndFavorite(collectionId, filmEntity, isCheck)

        insertFilmCollection(filmEntity, collectionId)
    }

    fun deleteFilmInCollection(collectionId: Int, filmID: Int) {
        val collectionEntity = filmsCollectionDao.getCollectionEntity(collectionId)
        collectionEntity.size--
        filmsCollectionDao.deleteFilm(MergeCollectionAndFilms(collectionId, filmID))
        filmsCollectionDao.updateCollection(collectionEntity)
    }

    fun deleteFilmInCollectionFromBottomShit(
        collectionId: Int,
        filmEntity: FilmEntity,
        isSelect: Boolean
    ) {

        likeAndFavorite(collectionId, filmEntity, isSelect)
        deleteFilmInCollection(collectionId, filmEntity.filmId)
    }

    fun addCollection(name: String, id: Int? = null) =
        filmsCollectionDao.insertCollection(FilmsCollectionEntity(id, name))


    fun getFilmsFromCollectionID(collectionID: Int) =
        filmsCollectionDao.getFilmsFromCollectionID(collectionID)

    fun getCollectionFromFilmId(id: Int?) = filmsCollectionDao.getCollectionsFromFilmID(id)

    fun getCollectionFromFilmIdAndCollectionId(filmID: Int, collectionId: Int) =
        filmsCollectionDao.getCollectionsFromFilmIDAndCollectionID(filmID, collectionId)

    fun getAllCollection() = filmsCollectionDao.getCollection()

    fun getFilmsFromListID(list: List<Int>) = filmsCollectionDao.getFilmsByListId(list)

    private fun likeAndFavorite(collectionId: Int, filmEntity: FilmEntity, isSelect: Boolean) {
        if (collectionId == ID_LIKE_COLLECTION) {
            filmEntity.isLike = isSelect
            filmsDao.updateFilm(filmEntity)
        }
        if (collectionId == ID_FAVORITE_COLLECTION) {
            filmEntity.isFavorite = isSelect
            filmsDao.updateFilm(filmEntity)
        }
    }

}