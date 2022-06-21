package com.example.hw_roomdao.presentation.contact_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw_roomdao.R
import com.example.hw_roomdao.databinding.FragmentChatListBinding
import com.example.hw_roomdao.databinding.FragmentUsersBinding
import com.example.hw_roomdao.presentation.contact_list.adapter.UserListAdapter
import com.example.hw_roomdao.utils.autoCleared

class ContactListFragment : Fragment(R.layout.fragment_users) {

    private lateinit var binding: FragmentUsersBinding
    private val viewModel by viewModels<ContactListViewModel>()
    private var contactListAdapter: UserListAdapter by autoCleared()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersBinding.bind(view)

        initToolBar()
        bindViewModel()
        viewModel.loadList()

    }

    private fun bindViewModel() {
        viewModel.contactListLiveData.observe(viewLifecycleOwner) {contactListAdapter.items = it}
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_contact_list_item)
    }
}