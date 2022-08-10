package com.example.hw_roomdao.presentation.employee_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.databinding.FragmentEmployeeAddBinding
import com.example.hw_roomdao.utils.hideKeyboardAndClearFocus
import com.example.hw_roomdao.utils.toast

class AddEmployeeFragment : Fragment(R.layout.fragment_employee_add) {

    private lateinit var binding: FragmentEmployeeAddBinding

    private val args: AddEmployeeFragmentArgs by navArgs()

    private val viewModel by viewModels<AddEmployeeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeeAddBinding.bind(view)

        initToolbar()
        bindViewModel()
        viewModel.init(args.id)
    }

    private fun initToolbar() {
        binding.appBar.toolBar.setTitle(R.string.employee_add_title)
        binding.appBar.toolBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViewModel() {
        binding.saveButton.setOnClickListener { saveEmployee() }
        viewModel.existingProjectWithEmployeeLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                setExistingEmployeeInfo(it)
            }
        }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun saveEmployee() {
        viewModel.save(
            id = args.id,
            firstName = binding.firstNameTextField.editText?.text?.toString().orEmpty(),
            lastName = binding.lastNameTextField.editText?.text?.toString().orEmpty(),
            email = binding.emailTextField.editText?.text?.toString().orEmpty(),
            age = 54
        )
    }

    private fun setExistingEmployeeInfo(employee: Employee) {
        binding.firstNameTextField.editText?.setText(employee.firstName)
        binding.lastNameTextField.editText?.setText(employee.lastName)
        binding.emailTextField.editText?.setText(employee.email)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}