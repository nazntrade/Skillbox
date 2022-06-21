package com.example.hw_roomdao.presentation.chat_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_roomdao.data.db.models.Chat
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ChatListAdapter(
    onUserClick: (Chat) -> Unit,
    onDeleteUser: (Chat) -> Unit
) : AsyncListDifferDelegationAdapter<Chat>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ChatsAdapterDelegate(onUserClick, onDeleteUser))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }

}
