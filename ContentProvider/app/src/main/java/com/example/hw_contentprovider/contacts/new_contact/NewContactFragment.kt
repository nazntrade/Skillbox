package com.example.hw_contentprovider.contacts.new_contact

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentNewContactBinding
import com.example.hw_contentprovider.utils.hideKeyboardAndClearFocus
import com.example.hw_contentprovider.utils.toast
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class NewContactFragment : Fragment(R.layout.fragment_new_contact) {

    lateinit var binding: FragmentNewContactBinding
    private val viewModel: NewContactViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewContactBinding.bind(view)

        bindViewModel()
    }

    private fun bindViewModel() {
        binding.addButton.setOnClickListener { saveContactWithPermissionCheck() }
        viewModel.saveSuccessViewModel.observe(viewLifecycleOwner) { findNavController().popBackStack() }
        viewModel.saveErrorViewModel.observe(viewLifecycleOwner) { toast(it) }
    }

    private fun saveContactWithPermissionCheck() {
        constructPermissionsRequest(
            Manifest.permission.WRITE_CONTACTS,
            onShowRationale = ::onContactPermissionShowRationale,
            onPermissionDenied = ::onContactPermissionDenied,
            onNeverAskAgain = ::onContactPermissionNeverAskAgain
        ) {
            viewModel.save(
                firstName = binding.addFirstNameEditText.text?.toString().orEmpty(),
                phone = binding.addPhoneEditText.text?.toString().orEmpty(),
                secondName = binding.addSecondNameEditText.text?.toString().orEmpty(),
                email = binding.addEmailEditText.text?.toString().orEmpty()
            )
        }.launch()
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.contact_permission_never_ask_again)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}