package com.example.hw_roomdao.data

import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Chat
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatListRepository {


    suspend fun saveChat(chat: Chat) {
        chatDao.insertChat(listOf(chat))
    }

    suspend fun updateChat(chat: Chat) {
        chatDao.updateChat(chat)
    }

    suspend fun removeChat(chatId: Long) {
        chatDao.removeChatById(chatId)
    }

    suspend fun getChattById(chatId: Long): Chat? {
        return chatDao.getChatById(chatId)
    }

    suspend fun getAllChats(): List<Chat> = withContext(Dispatchers.IO) {

        listOf()
    }

}