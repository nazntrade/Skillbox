package com.example.feature_database.entity

import androidx.room.*

@Entity(
    tableName = "films_collections",
    indices = [Index("name", unique = true)]
)
class FilmsCollectionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val nameCollection: String,
    @ColumnInfo(name = "size")
    var size: Int = 0
)