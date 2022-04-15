package com.example.hw_files.files

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw_files.R
import com.example.hw_files.databinding.FragmentFileBinding

class FilesFragment: Fragment(R.layout.fragment_file) {

    lateinit var binding: FragmentFileBinding
    private val viewModel: FilesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFileBinding.bind(view)


    }
}