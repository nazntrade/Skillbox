package com.example.hw_roomdao.presentation.contact_add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.databinding.FragmentContactAddBinding
import com.example.hw_roomdao.utils.hideKeyboardAndClearFocus
import com.example.hw_roomdao.utils.toast

class AddContactFragment: Fragment(R.layout.fragment_contact_add) {

    lateinit var binding: FragmentContactAddBinding

    private val args: AddContactFragmentArgs by navArgs()

    private val viewModel by viewModels<AddContactViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactAddBinding.bind(view)

        initToolbar()
        bindViewModel()
        viewModel.init(args.id)
    }

    private fun initToolbar() {
        binding.appBar.toolBar.setTitle(R.string.user_add_title)
        binding.appBar.toolBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViewModel() {
        binding.saveButton.setOnClickListener { saveUser() }
        viewModel.existingContactLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                setExistingUserInfo(it)
            }
        }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun saveUser() {
        viewModel.save(
            id = args.id,
            firstName = binding.firstNameTextField.editText?.text?.toString().orEmpty(),
            lastName = binding.lastNameTextField.editText?.text?.toString().orEmpty(),
            email = binding.emailTextField.editText?.text?.toString().orEmpty(),
            avatar = binding.avatarTextField.editText?.text?.toString().orEmpty()
        )
    }

    private fun setExistingUserInfo(contact: Contact) {
        binding.firstNameTextField.editText?.setText(contact.firstName)
        binding.lastNameTextField.editText?.setText(contact.lastName)
        binding.emailTextField.editText?.setText(contact.email)
        binding.avatarTextField.editText?.setText(contact.avatar)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}