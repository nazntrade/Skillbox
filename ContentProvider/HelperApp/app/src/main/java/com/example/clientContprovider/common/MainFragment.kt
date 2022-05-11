package com.example.clientContprovider.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.clientContprovider.R
import com.example.clientContprovider.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.contactsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToContactsFragment())
        }
    }
}