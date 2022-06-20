package com.example.hw_roomdao.presentation.contact_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hw_roomdao.R
import com.example.hw_roomdao.databinding.FragmentChatListBinding

class ContactListFragment: Fragment(R.layout.fragment_users) {

    private lateinit var binding: FragmentChatListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatListBinding.bind(view)

        initToolBar()

    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_contact_list_item)
    }
}