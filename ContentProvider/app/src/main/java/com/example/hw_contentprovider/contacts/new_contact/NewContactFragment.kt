package com.example.hw_contentprovider.contacts.new_contact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentNewContactBinding

class NewContactFragment : Fragment(R.layout.fragment_new_contact) {

    lateinit var binding: FragmentNewContactBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewContactBinding.bind(view)

        navigateToContacts()
    }

    private fun navigateToContacts() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(NewContactFragmentDirections.actionNewContactFragmentToContactsFragment())
        }
    }
}