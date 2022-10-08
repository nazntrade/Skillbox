package com.example.hw_contentprovider.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.contactsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToContactsFragment())
        }

        binding.shareFilesButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToFilesShareFragment())
        }
    }
}