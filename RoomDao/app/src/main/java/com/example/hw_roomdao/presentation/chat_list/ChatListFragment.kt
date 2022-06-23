package com.example.hw_roomdao.presentation.chat_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_roomdao.R
import com.example.hw_roomdao.databinding.FragmentChatListBinding

//контакт передал из списка контактов. Теперь нужно тут написать 1-адаптерЧатов, 2-добавление к общему списку нового чата

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    private lateinit var binding: FragmentChatListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatListBinding.bind(view)

        initToolBar()
        addNewChat()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_chat_list_item)
    }

    private fun addNewChat() {
        binding.addNewChatButton.setOnClickListener {
            findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToContactListFragment())
        }
    }
}