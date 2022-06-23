package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = ContactContract.TABLE_NAME)
data class Contact(
    @ColumnInfo(name = ContactContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ContactContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = ContactContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = ContactContract.Columns.EMAIL)
    val email: String,
    @ColumnInfo(name = ContactContract.Columns.AVATAR)
    val avatar: Int
)