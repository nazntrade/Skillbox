package com.example.clientContprovider.contacts.courseList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientContprovider.R
import com.example.clientContprovider.contacts.adapter.CourseListAdapter
import com.example.clientContprovider.contacts.data.Course
import com.example.clientContprovider.databinding.FragmentContactsListBinding
import com.example.clientContprovider.utils.autoCleared

class CourseListFragment : Fragment(R.layout.fragment_contacts_list) {

    private val viewModel by viewModels<CourseListViewModel>()
    private lateinit var binding: FragmentContactsListBinding
    private var courseListAdapter: CourseListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsListBinding.bind(view)

        viewModel.loadList()
        navigateToAddNewCourse()
        initList()
        initToolBar()
        bindViewModel()
        deleteAll()
    }

    private fun deleteAll() {
        binding.deleteAllCourseButton.setOnClickListener {
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("Delete all")
                    .setMessage("Are you sure you want to delete all courses?")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { _, _ ->
                        viewModel.deleteAllCourses()
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }
    }

    private fun bindViewModel() {
        viewModel.courseListLiveData.observe(viewLifecycleOwner) { courseListAdapter.items = it }
        viewModel.deleteSuccessLiveData.observe(viewLifecycleOwner) {
            courseListAdapter.items = emptyList()
        }
//        viewModel.errorLiveData.observe(viewLifecycleOwner) {
//            Toast.makeText(context, it, Toast.LENGTH_LONG ).show()
//        }
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolBarCourses)
    }

    private fun initList() {
        courseListAdapter = CourseListAdapter { course: Course -> navigate(course) }
        with(binding.courseListRecyclerView) {
            adapter = courseListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun navigate(course: Course) {
        val action =
            CourseListFragmentDirections.actionContactsListFragmentToDetailContactInfoFragment(
                course
            )
        findNavController().navigate(action)
    }

    private fun navigateToAddNewCourse() {
        binding.addNewCourseButton.setOnClickListener {
            findNavController().navigate(CourseListFragmentDirections.actionContactsListFragmentToNewContactFragment())
        }
    }
}