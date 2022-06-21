package com.example.hw_roomdao.presentation.contact_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.databinding.FragmentUsersBinding
import com.example.hw_roomdao.presentation.contact_list.adapter.ContactListAdapter
import com.example.hw_roomdao.utils.autoCleared

class ContactListFragment : Fragment(R.layout.fragment_users) {

    private lateinit var binding: FragmentUsersBinding
    private val viewModel by viewModels<ContactListViewModel>()
    private var contactListAdapter: ContactListAdapter by autoCleared()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersBinding.bind(view)

        initToolBar()
        initList()
        bindViewModel()
        viewModel.loadList()

    }

    private fun bindViewModel() {
        viewModel.contactListLiveData.observe(viewLifecycleOwner) {contactListAdapter.items = it}
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_contact_list_item)
    }

    private fun initList() {
        contactListAdapter = ContactListAdapter(::navigate, viewModel::removeUser)
        with(binding.userList) {
            adapter = contactListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun navigate(contact: Contact) {
        val direction = ContactListFragmentDirections.actionContactListFragmentToChatListFragment2(contact.id)
        findNavController().navigate(direction)
    }

}