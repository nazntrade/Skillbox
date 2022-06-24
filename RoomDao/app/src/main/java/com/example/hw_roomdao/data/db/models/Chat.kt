package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ChatContract.TABLE_NAME)
data class Chat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ChatContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ChatContract.Columns.TITLE)
    val title: String
)
