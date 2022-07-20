package com.example.hw_roomdao.presentation.chat_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Chat
import com.example.hw_roomdao.databinding.FragmentChatListBinding
import com.example.hw_roomdao.presentation.chat_list.adapter.ChatListAdapter
import com.example.hw_roomdao.utils.autoCleared

//контакт передал из списка контактов. Теперь нужно тут написать 1-адаптерЧатов, 2-добавление к общему списку нового чата

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    private lateinit var binding: FragmentChatListBinding
    private val chatListViewModel by viewModels<ChatListViewModel>()
    private var chatListAdapter: ChatListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatListBinding.bind(view)

        initToolBar()
        initList()
        bindViewModel()
        chatListViewModel.loadList()
        addNewChat()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_chat_list_item)
    }

    private fun initList() {
        chatListAdapter = ChatListAdapter(::navigateToMessagesInChat, chatListViewModel::removeChat)
        with(chatList) {
            adapter = chatListAdapter
            setHasFixedSize(true)
        }
    }
    
    private fun bindViewModel() {
        chatListViewModel.chatsLiveData.observe(viewLifecycleOwner) { chatListAdapter.items = it }
    }

    private fun navigateToMessagesInChat(chat: Chat) {
//        val direction = UsersFragmentDirections.actionUsersFragmentToAddUserFragment(user.id)
//        findNavController().navigate(direction)
    }

    private fun addNewChat() {
        binding.addNewChatButton.setOnClickListener {
            findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToContactListFragment())
        }
    }
}