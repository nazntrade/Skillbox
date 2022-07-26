package com.example.hw_roomdao.presentation.chat_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.data.ChatsRepository
import com.example.hw_roomdao.data.db.models.Chat
import kotlinx.coroutines.launch
import timber.log.Timber

class ChatListViewModel : ViewModel() {

    private val chatsRepository = ChatsRepository()

    private val chatListMutableLiveData = MutableLiveData<List<Chat>>()

    val chatsLiveData: LiveData<List<Chat>>
        get() = chatListMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {

                chatListMutableLiveData.postValue(chatsRepository.getAllChats())
            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                chatListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeChat(chat: Chat) {
        viewModelScope.launch {
            try {
                chatsRepository.removeChat(chat.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}