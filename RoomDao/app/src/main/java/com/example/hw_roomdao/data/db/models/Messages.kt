package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MessagesContract.TABLE_NAME)
data class Messages(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MessagesContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = MessagesContract.Columns.CHAT_ID)
    val chatId: Long,
    @ColumnInfo(name = MessagesContract.Columns.BODY)
    val body: String,
    @ColumnInfo(name = MessagesContract.Columns.TIME)
    val time: String
)
