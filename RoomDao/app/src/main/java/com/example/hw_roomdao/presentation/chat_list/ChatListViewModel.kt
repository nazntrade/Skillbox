package com.example.hw_roomdao.presentation.chat_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.data.ChatListRepository
import com.example.hw_roomdao.data.db.models.Chat
import kotlinx.coroutines.launch
import timber.log.Timber

class ChatListViewModel : ViewModel() {

    private val chatListRepository = ChatListRepository()

    private val chatListMutableLiveData = MutableLiveData<List<Chat>>()

    val chatsLiveData: LiveData<List<Chat>>
        get() = chatListMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {

                chatListMutableLiveData.postValue(chatListRepository.getAllChats())
            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                chatListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeChat(chat: Chat) {
        viewModelScope.launch {
            try {
                chatListRepository.removeChat(chat.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}