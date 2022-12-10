package com.example.feature_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "merge",
    primaryKeys = ["collection_id", "film_id_collection"],
    foreignKeys = [
        ForeignKey(
            entity = FilmsCollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collection_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FilmEntity::class,
            parentColumns = ["film_id"],
            childColumns = ["film_id_collection"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class MergeCollectionAndFilms(
    @ColumnInfo(name = "collection_id")
    val collectionID: Int,
    @ColumnInfo(name = "film_id_collection")
    val filmID: Int,
)