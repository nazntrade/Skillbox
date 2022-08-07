package com.example.hw_roomdao.presentation.project_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.databinding.FragmentProjectListBinding
import com.example.hw_roomdao.presentation.project_list.adapter.ProjectListAdapter
import com.example.hw_roomdao.utils.autoCleared
import okhttp3.internal.wait

class ProjectListFragment : Fragment(R.layout.fragment_project_list) {

    private lateinit var binding: FragmentProjectListBinding
    private val projectListViewModel by viewModels<ProjectListViewModel>()
    private var projectListAdapter: ProjectListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProjectListBinding.bind(view)
        projectListViewModel.initExistedCompanyWithDirectorWithProjects(requireContext())
        initToolBar()
        initList()
        bindViewModel()
        addNewProject()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolbar_project_list_item)
    }

    private fun initList() {
        projectListAdapter =
            ProjectListAdapter(
                ::navigateToProjectWithEmployees,
                projectListViewModel::removeProject
            )
        with(binding.projectListRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = projectListAdapter
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        projectListViewModel.projectsLiveData.observe(viewLifecycleOwner) {
            projectListAdapter.items = it
        }

        projectListViewModel.directorLiveData.observe(viewLifecycleOwner) {
            val currentDirector = it
            binding.whoDirectorTextView.text = "Dir: ${currentDirector.directorName}"

            binding.directorLayout.setOnClickListener {
                val direction = ProjectListFragmentDirections
                    .actionProjectListFragmentToHireDirectorFragment(currentDirector)
                findNavController().navigate(direction)
            }
        }

    }

    private fun navigateToProjectWithEmployees(selectedProject: Project) {
        val direction = ProjectListFragmentDirections
            .actionProjectListFragmentToProjectWithEmployeesListFragment(selectedProject)
        findNavController().navigate(direction)
    }

    private fun addNewProject() {
        binding.addNewProjectButton.setOnClickListener {
            val direction =
                ProjectListFragmentDirections
                    .actionProjectListFragmentToAddProjectFragment(projectListViewModel.currentCompany)
            findNavController().navigate(direction)
        }
    }

}