package com.example.hw_contentprovider.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentContactsListBinding

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    lateinit var binding: FragmentContactsListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsListBinding.bind(view)

        navigateToAddNewContact()
    }

    private fun navigateToAddNewContact(){
        binding.addNewContactButton.setOnClickListener {
            findNavController().navigate(ContactsListFragmentDirections.actionContactsListFragmentToNewContactFragment())
        }
    }

}