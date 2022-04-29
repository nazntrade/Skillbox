package com.example.hw_contentprovider.contacts

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.contacts.adapter.ContactListAdapter
import com.example.hw_contentprovider.contacts.data.Contact
import com.example.hw_contentprovider.databinding.FragmentContactsListBinding
import permissions.dispatcher.ktx.constructPermissionsRequest
import com.example.hw_contentprovider.utils.autoCleared
import com.example.hw_contentprovider.utils.toast
import permissions.dispatcher.PermissionRequest

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private val viewModel by viewModels<ContactsListViewModel>()
    private lateinit var binding: FragmentContactsListBinding
    private var contactListAdapter: ContactListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsListBinding.bind(view)

        getPermissionAndLoadContactList()
        navigateToAddNewContact()
        initList()
        initToolBar()
        bindViewModel()
    }

    private fun getPermissionAndLoadContactList() {
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

    private fun bindViewModel() {

    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolBarContacts)
    }

    private fun initList() {
        contactListAdapter = ContactListAdapter { contact: Contact -> navigate(contact) }
        with(binding.contactListRecyclerView) {
            adapter = contactListAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigate(contact: Contact) {
        val action =
            ContactsListFragmentDirections.actionContactsListFragmentToDetailContactInfoFragment(
                contact
            )

        findNavController().navigate(action)
    }

    private fun navigateToAddNewContact() {
        binding.addNewContactButton.setOnClickListener {
            findNavController().navigate(ContactsListFragmentDirections.actionContactsListFragmentToNewContactFragment())
        }
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_add_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.contact_add_permission_never_ask_again)
    }

}