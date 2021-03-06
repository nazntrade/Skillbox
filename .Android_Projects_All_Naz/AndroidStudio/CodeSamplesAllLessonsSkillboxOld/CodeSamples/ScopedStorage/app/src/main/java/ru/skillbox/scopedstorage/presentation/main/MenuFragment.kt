package ru.skillbox.scopedstorage.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.skillbox.scopedstorage.databinding.FragmentMenuBinding
import ru.skillbox.scopedstorage.utils.ViewBindingFragment

class MenuFragment : ViewBindingFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagesButton.setOnClickListener {
            val direction = MenuFragmentDirections.actionMenuFragmentToImagesFragment()
            findNavController().navigate(direction)
        }
        binding.docsButton.setOnClickListener {
            val direction = MenuFragmentDirections.actionMenuFragmentToDocsFragment()
            findNavController().navigate(direction)
        }
    }
}