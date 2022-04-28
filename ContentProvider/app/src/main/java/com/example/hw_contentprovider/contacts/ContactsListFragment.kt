package com.example.hw_contentprovider.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.contacts.adapter.ContactListAdapter
import com.example.hw_contentprovider.contacts.data.Contact
import com.example.hw_contentprovider.databinding.FragmentContactsListBinding
import com.example.hw_contentprovider.utils.autoCleared

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    lateinit var binding: FragmentContactsListBinding
    private var contactListAdapter: ContactListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsListBinding.bind(view)

        navigateToAddNewContact()
        initList()
    }

    private fun initList() {
        contactListAdapter = ContactListAdapter { contact: Contact -> navigate(contact) }
        with(binding.contactListRecyclerView) {
            adapter = contactListAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigate(contact: Contact) {
        val action =
            ContactsListFragmentDirections.actionContactsListFragmentToDetailContactInfoFragment(
                contact)

        findNavController().navigate(action)
    }

    private fun navigateToAddNewContact() {
        binding.addNewContactButton.setOnClickListener {
            findNavController().navigate(ContactsListFragmentDirections.actionContactsListFragmentToNewContactFragment())
        }
    }

}