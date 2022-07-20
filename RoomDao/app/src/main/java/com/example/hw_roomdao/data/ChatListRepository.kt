package com.example.hw_roomdao.data

import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Chat
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatListRepository {

    private val chatDao = Database.instance.chatDao()

    suspend fun saveChat(chat: Chat) {
//        chatDao.insertChat(listOf(chat))
    }

    suspend fun updateChat(chat: Chat) {
//        chatDao.updateChat(chat)
    }

    suspend fun removeChat(chatId: Long) {
//        chatDao.removeChatById(chatId)
    }

//    suspend fun getChatById(chatId: Long): Chat? {
//        return chatDao.getChatById(chatId)
//    }

    suspend fun getAllChats(): List<Chat> {

        return chatDao.getAllChats()
    }

}