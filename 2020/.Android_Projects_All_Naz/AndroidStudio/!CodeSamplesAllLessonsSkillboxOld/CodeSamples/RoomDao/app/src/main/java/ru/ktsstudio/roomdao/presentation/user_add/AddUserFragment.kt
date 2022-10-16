package ru.ktsstudio.roomdao.presentation.user_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_user_add.*
import kotlinx.android.synthetic.main.view_toolbar.*
import ru.ktsstudio.roomdao.R
import ru.ktsstudio.roomdao.data.db.models.User
import ru.ktsstudio.roomdao.utils.hideKeyboardAndClearFocus
import ru.ktsstudio.roomdao.utils.toast

class AddUserFragment : Fragment(R.layout.fragment_user_add) {

    private val args: AddUserFragmentArgs by navArgs()

    private val viewModel by viewModels<AddUserViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        bindViewModel()
        viewModel.init(args.id)
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.user_add_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViewModel() {
        saveButton.setOnClickListener { saveUser() }
        viewModel.existingUserLiveData.observe(viewLifecycleOwner) { setExistingUserInfo(it) }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun saveUser() {
        viewModel.save(
            id = args.id,
            firstName = firstNameTextField.editText?.text?.toString().orEmpty(),
            lastName = lastNameTextField.editText?.text?.toString().orEmpty(),
            email = emailTextField.editText?.text?.toString().orEmpty(),
            avatar = avatarTextField.editText?.text?.toString().orEmpty()
        )
    }

    private fun setExistingUserInfo(user: User) {
        firstNameTextField.editText?.setText(user.firstName)
        lastNameTextField.editText?.setText(user.lastName)
        emailTextField.editText?.setText(user.email)
        avatarTextField.editText?.setText(user.avatar)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}