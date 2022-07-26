package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ProjectContract.TABLE_NAME)
data class Project(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ProjectContract.Columns.TITLE)
    val title: String
)
