package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ContactChatsContract.TABLE_NAME)
data class ContactChats(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ContactChatsContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ContactChatsContract.Columns.CONTACT_ID)
    val contactId: Long,
    @ColumnInfo(name = ContactChatsContract.Columns.CHAT_ID)
    val chatId: Long
)