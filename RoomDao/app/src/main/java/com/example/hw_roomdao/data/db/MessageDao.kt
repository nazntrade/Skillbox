package com.example.hw_roomdao.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.hw_roomdao.data.db.models.Messages
import com.example.hw_roomdao.data.db.models.MessagesContract

@Dao
interface MessageDao {

    fun insertMessage(messages: Messages)

    fun removeMessage(messages: Messages)

    @Query("SELECT * FROM ${MessagesContract.TABLE_NAME}")
    fun getAllMessages(): List<Messages>

}