package com.becker.beckerSkillCinema.data.localData.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "CustomCollection",
    primaryKeys = ["collectionName", "movieId"]
)
data class CustomCollection(
    @ColumnInfo(name = "collectionName")
    val collectionName: String,
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "dateAdded")
    val dateAdded: String
)
