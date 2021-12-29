package com.example.hw_ViewModelAndNavigation.petShop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hw_ViewModelAndNavigation.R
import com.example.hw_ViewModelAndNavigation.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    lateinit var binding: FragmentDetailsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
    }
}

