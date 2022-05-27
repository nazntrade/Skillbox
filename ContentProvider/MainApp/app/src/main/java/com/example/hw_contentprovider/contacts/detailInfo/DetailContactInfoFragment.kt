package com.example.hw_contentprovider.contacts.detailInfo

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentDetailInfoBinding
import com.example.hw_contentprovider.utils.toast
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import kotlin.getValue

class DetailContactInfoFragment : Fragment(R.layout.fragment_detail_info) {

    private val viewModel by viewModels<DetailContactInfoViewModel>()
    private val args: DetailContactInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailInfoBinding.bind(requireView())

        binding.detailNameTextView.text = args.currentContact.name
        binding.detailPhonesTextView.text = args.currentContact.phones.joinToString("\n")
        binding.detailEmailsTextView.text = args.currentContact.emails.joinToString("\n")

        bindViewModel()
    }

    private fun bindViewModel() {
        binding.deleteButton.setOnClickListener { getPermissionAndDeleteContact() }
        viewModel.deleteSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun getPermissionAndDeleteContact() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.WRITE_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { viewModel.deleteContactViewModel(args) }
            )
                .launch()
        }
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
}