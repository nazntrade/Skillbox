package com.example.hw_roomdao.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.hw_roomdao.data.db.models.Chat
import com.example.hw_roomdao.data.db.models.ChatContract

@Dao
interface ChatDao {

    fun insertChat(chat: Chat)

    fun removeChat(chat: Chat)

    @Query("SELECT * FROM ${ChatContract.TABLE_NAME}")
    fun getAllChats(): List<Chat>

}