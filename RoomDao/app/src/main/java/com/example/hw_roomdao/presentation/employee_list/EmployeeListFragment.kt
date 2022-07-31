package com.example.hw_roomdao.presentation.employee_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.databinding.FragmentEmployeesBinding
import com.example.hw_roomdao.presentation.employee_list.adapter.EmployeeListAdapter
import com.example.hw_roomdao.utils.autoCleared

class EmployeeListFragment : Fragment(R.layout.fragment_employees) {

    private lateinit var binding: FragmentEmployeesBinding
    private val viewModel by viewModels<EmployeeListViewModel>()
    private val incomingArgs: EmployeeListFragmentArgs by navArgs()
    private var employeeListAdapter: EmployeeListAdapter by autoCleared()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeesBinding.bind(view)

        initToolBar()
        initList()
        bindViewModel()
        viewModel.loadList()
        addNewEmployee()
    }

    private fun bindViewModel() {
        viewModel.employeeListLiveData.observe(viewLifecycleOwner) {
            employeeListAdapter.items = it
        }
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_employee_list_item)
    }

    private fun initList() {
        employeeListAdapter =
            EmployeeListAdapter(
                ::addEmployeeToProjectListAndNavigateBack,
                viewModel::removeEmployee
            )
        with(binding.employeeList) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = employeeListAdapter
            setHasFixedSize(true)
        }
    }

    private fun addEmployeeToProjectListAndNavigateBack(selectedEmployee: Employee) {
        val selectedProject = incomingArgs.selectedProject
        viewModel.addSelectedEmployeeToCurrentProject(selectedProject, selectedEmployee)
        findNavController().popBackStack()
    }

    private fun addNewEmployee() {
        binding.addNewEmployeeButton.setOnClickListener {
            findNavController().navigate(EmployeeListFragmentDirections.actionContactListFragmentToAddContactFragment())
        }
    }
}