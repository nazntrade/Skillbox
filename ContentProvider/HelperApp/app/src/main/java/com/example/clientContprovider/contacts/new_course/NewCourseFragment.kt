package com.example.clientContprovider.contacts.new_course

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.clientContprovider.R
import com.example.clientContprovider.databinding.FragmentNewCourseBinding
import com.example.clientContprovider.utils.hideKeyboardAndClearFocus
import com.example.clientContprovider.utils.toast

class NewCourseFragment : Fragment(R.layout.fragment_new_course) {

    private lateinit var binding: FragmentNewCourseBinding
    private val viewModel: NewCourseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewCourseBinding.bind(view)

        bindViewModel()
    }

    private fun bindViewModel() {
        binding.addButton.setOnClickListener {
            viewModel.save(
                title = binding.addTitleEditText.text?.toString().orEmpty(),
            )
        }
        viewModel.saveSuccessViewModel.observe(viewLifecycleOwner) { findNavController().popBackStack() }
        viewModel.saveErrorViewModel.observe(viewLifecycleOwner) { toast(it) }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}