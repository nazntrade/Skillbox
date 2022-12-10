package com.example.feature_database

import com.example.core.R
import com.example.core.tools.*
import com.example.core.tools.base_model.FilmsCollection

import com.example.core.tools.base_model.films.BaseFilm
import com.example.core.tools.base_model.films.FilmsDTO
import com.example.core.tools.category.NestedCollection
import com.example.feature_database.entity.FilmsCollectionEntity

fun List<FilmsCollectionEntity?>.toNestedCollection(): List<NestedCollection> {
    val list = mutableListOf<NestedCollection>()
    list.add(
        NestedCollection(
            null,
            "Создать свою коллекцию",
            0,
            R.drawable.ic_close,
            false
        )
    )
    this.forEach {
        fun addList(
            collection: FilmsCollectionEntity,
            icon: Int = R.drawable.ic_profile,
            isClose: Boolean = true
        ) {
            list.add(
                NestedCollection(
                    collection.id, collection.nameCollection, collection.size,
                    icon, isClose
                )
            )
        }

        if (it != null) {
            if (it.id != ID_LOOK_COLLECTION && it.id != ID_HISTORY_COLLECTION)
                when (it.id) {
                    ID_FAVORITE_COLLECTION ->
                        addList(it, R.drawable.ic_favorite, false)
                    ID_LIKE_COLLECTION ->
                        addList(it, R.drawable.ic_like, false)
                    else -> addList(it)
                }
        }
    }
    return list
}

fun List<FilmsDTO?>.toNestedFilms(): MutableList<BaseFilm> {
    val list = mutableListOf<BaseFilm>()
    this.forEach {
        if (it != null)
            list.add(BaseFilm(it.nameRu, it.posterUrlPreview, it.genres, it.rating, it.filmId))
    }
    return list
}


fun List<FilmsCollectionEntity>.toFilmsCollectionList(list: List<Int>?): MutableList<FilmsCollection> {
    val newList = mutableListOf<FilmsCollection>()
    this.forEach { collection ->
        if (collection.id!= ID_LOOK_COLLECTION && collection.id!= ID_HISTORY_COLLECTION ){
            val item = FilmsCollection(collection.id, collection.nameCollection, collection.size, false)
            list?.forEach { id ->
                if (id == item.id) item.isCheck = true
            }
            newList.add(item)
        }
    }
    return newList
}