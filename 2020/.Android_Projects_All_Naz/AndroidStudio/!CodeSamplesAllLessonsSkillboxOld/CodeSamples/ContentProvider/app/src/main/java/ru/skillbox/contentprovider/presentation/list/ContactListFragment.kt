package ru.skillbox.contentprovider.presentation.list

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_contact_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import ru.skillbox.contentprovider.R
import ru.skillbox.contentprovider.presentation.list.adapter.ContactListAdapter
import ru.skillbox.contentprovider.utils.autoCleared
import ru.skillbox.contentprovider.utils.toast

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    private val viewModel by viewModels<ContactListViewModel>()
    private var contactAdapter: ContactListAdapter by autoCleared()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        initList()
        addContactButton.setOnClickListener {
            findNavController().navigate(ContactListFragmentDirections.actionContactListFragmentToContactAddFragment())
        }
        bindViewModel()

        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { viewModel.loadList() }
            )
                .launch()
        }
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.contact_list_title)
    }

    private fun initList() {
        contactAdapter = ContactListAdapter(viewModel::callToContact)
        with(contactList) {
            adapter = contactAdapter
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { contactAdapter.items = it }
        viewModel.callLiveData.observe(viewLifecycleOwner, ::callToPhone)
    }

    private fun callToPhone(phone: String) {
        Intent(Intent.ACTION_DIAL)
            .apply { data = Uri.parse("tel:$phone") }
            .also { startActivity(it) }
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_list_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.contact_list_permission_never_ask_again)
    }
}