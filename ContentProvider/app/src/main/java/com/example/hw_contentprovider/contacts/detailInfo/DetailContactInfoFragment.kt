package com.example.hw_contentprovider.contacts.detailInfo

import android.Manifest
import android.content.ContentProviderOperation
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentDetailInfoBinding
import com.example.hw_contentprovider.utils.toast
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import java.lang.String
import kotlin.arrayOf
import kotlin.getValue


class DetailContactInfoFragment : Fragment(R.layout.fragment_detail_info) {

    private val args: DetailContactInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailInfoBinding.bind(requireView())

        binding.detailNameTextView.text = args.currentContact.name
        binding.detailPhonesTextView.text = args.currentContact.phones.joinToString("\n")
        binding.detailEmailsTextView.text = args.currentContact.emails.joinToString("\n")

        binding.deleteButton.setOnClickListener {
            getPermission()
        }
    }

    private fun getPermission() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.WRITE_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { deleteContact() }
            )
                .launch()
        }
    }

    private fun deleteContact() {
        val ops = ArrayList<ContentProviderOperation>()
        ops.add(
            ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(
                    ContactsContract.RawContacts._ID + "=?",
                    arrayOf(String.valueOf(args.currentContact.id))
                )
                .build()
        )
        context?.contentResolver?.applyBatch(ContactsContract.AUTHORITY, ops)

        findNavController().popBackStack()
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