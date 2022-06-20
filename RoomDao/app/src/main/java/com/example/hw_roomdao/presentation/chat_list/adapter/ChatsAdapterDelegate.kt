package com.example.hw_roomdao.presentation.chat_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Chat
import com.example.hw_roomdao.databinding.FragmentItemChatBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ChatsAdapterDelegate(
    private val onChatClick: (Chat) -> Unit,
    private val onDeleteChat: (Chat) -> Unit
): AbsListItemAdapterDelegate<Chat, Chat, ChatsAdapterDelegate.Holder>() {

    override fun isForViewType(item: Chat, items: MutableList<Chat>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(FragmentItemChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onChatClick, onDeleteChat)
    }

    override fun onBindViewHolder(item: Chat, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemChatBinding,
        onChatClick: (Chat) -> Unit,
        onDeleteChat: (Chat) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView = binding.chatTitleTextView
        private val iconImageView = binding.iconChatImageView

        private var currentChat: Chat? = null

        init {
            binding.root.setOnClickListener { currentChat?.let(onChatClick) }
            binding.removeButton.setOnClickListener { currentChat?.let(onDeleteChat) }
        }

        fun bind(chat: Chat) {
            currentChat = chat
            nameTextView.text = chat.title
            iconImageView.load(R.drawable.chat_icon)
        }
    }
}
