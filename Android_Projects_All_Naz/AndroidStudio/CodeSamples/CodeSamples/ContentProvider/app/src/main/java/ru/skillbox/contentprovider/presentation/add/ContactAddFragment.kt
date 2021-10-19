package ru.skillbox.contentprovider.presentation.add

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_contact_add.*
import kotlinx.android.synthetic.main.view_toolbar.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import ru.skillbox.contentprovider.R
import ru.skillbox.contentprovider.utils.hideKeyboardAndClearFocus
import ru.skillbox.contentprovider.utils.toast

/**
 * @author Maxim Myalkin (MaxMyalkin) on 27.09.2020.
 */
class ContactAddFragment: Fragment(R.layout.fragment_contact_add) {

    private val viewModel by viewModels<ContactAddViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        bindViewModel()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.contact_add_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViewModel() {
        saveButton.setOnClickListener { saveContactWithPermissionCheck() }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun saveContactWithPermissionCheck() {
        constructPermissionsRequest(
            Manifest.permission.WRITE_CONTACTS,
            onShowRationale = ::onContactPermissionShowRationale,
            onPermissionDenied = ::onContactPermissionDenied,
            onNeverAskAgain = ::onContactPermissionNeverAskAgain) {
            viewModel.save(
                name = nameTextField.editText?.text?.toString().orEmpty(),
                phone = phoneTextField.editText?.text?.toString().orEmpty()
            )
        }.launch()
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

    override fun onDestroy() {
        super.onDestroy()
        if(isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }

}