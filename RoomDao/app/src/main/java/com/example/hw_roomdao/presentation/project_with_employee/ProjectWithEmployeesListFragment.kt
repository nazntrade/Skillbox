package com.example.hw_roomdao.presentation.project_with_employee

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

class ProjectWithEmployeesListFragment : Fragment(R.layout.fragment_employees) {

    private lateinit var binding: FragmentEmployeesBinding
    private val incomingArgs: ProjectWithEmployeesListFragmentArgs by navArgs()
    private val viewModel by viewModels<ProjectWithEmployeesListViewModel>()
    private var employeeListAdapter: EmployeeListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeesBinding.bind(view)

        initToolBar()
        initList()
        bindViewModel()
//        viewModel.loadList()
        addNewEmployeeToCurrentProject()
    }

    private fun bindViewModel() {
//        viewModel.projectWithEmployeesListLiveData.observe(viewLifecycleOwner) {
//            employeeListAdapter.items = it
//        }
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = "Employees in ${incomingArgs.selectedProject.title}"
    }

    private fun initList() {
//        employeeListAdapter =
//            EmployeeListAdapter(
//                ::addEmployeeToProjectListAndNavigateBack,
//                viewModel::removeEmployee
//            )
//        with(binding.employeeList) {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = employeeListAdapter
//            setHasFixedSize(true)
//        }
    }

    private fun addEmployeeToProjectListAndNavigateBack(selectedEmployee: Employee) {
        //some action
    }

    private fun addNewEmployeeToCurrentProject() {
        binding.addNewEmployeeButton.setOnClickListener {
            findNavController().navigate(
                ProjectWithEmployeesListFragmentDirections
                    .actionProjectWithEmployeesListFragmentToEmployeeListFragment()
            )

            // to add action in order to add new employee to current project

        }
    }
}