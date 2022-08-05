package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DirectorContract.TABLE_NAME)
data class Director(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DirectorContract.Columns.ID)
    val directorId: Long,
    @ColumnInfo(name = DirectorContract.Columns.DIRECTOR_NAME)
    val directorName: String
)
