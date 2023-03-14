package com.becker.beckerSkillCinema.data.localData.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Watched")
data class Watched(
    @PrimaryKey
    @ColumnInfo(name = "watchedId")
    val watchedId: Int,
    @ColumnInfo(name = "dateAdded")
    val dateAdded: String
)
