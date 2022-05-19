package com.example.clientContprovider.contacts.detailInfo

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.clientContprovider.R
import com.example.clientContprovider.contacts.data.Course
import com.example.clientContprovider.databinding.FragmentDetailInfoBinding
import com.example.clientContprovider.utils.toast
import kotlin.getValue

class DetailCourseInfoFragment : Fragment(R.layout.fragment_detail_info) {

    private val viewModel by viewModels<DetailCourseInfoViewModel>()
    private val args: DetailCourseInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailInfoBinding.bind(requireView())

        showTitle(args.currentCourse.title)
        deleteCourse(args)
        editCourse(args)
        bindViewModel()
    }

    private fun showTitle(title: String) {
        binding.detailNameTextView.text = title
    }

    private fun bindViewModel() {
        viewModel.deleteSuccessLiveData.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.updateSuccessLiveData.observe(viewLifecycleOwner) {
            showTitle(it)
        }
//        viewModel.errorLiveData.observe(viewLifecycleOwner) {
//            Toast.makeText(context, it, Toast.LENGTH_LONG ).show()
//        }
    }

    private fun editCourse(args: DetailCourseInfoFragmentArgs) {
        binding.editButton.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_edit_course, null)
            val dialogNameTextView = view.findViewById<EditText>(R.id.edit_course_button)
            dialogNameTextView.setText(args.currentCourse.title)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(view)
            builder.setPositiveButton("Ok") { _, _ ->
                val editedCourse =
                    Course(id = args.currentCourse.id, title = dialogNameTextView.text.toString())
                viewModel.editCourse(editedCourse)
            }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    private fun deleteCourse(args: DetailCourseInfoFragmentArgs) {
        binding.deleteButton.setOnClickListener {
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("Delete course")
                    .setMessage("Are you sure you want to delete this course?")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { _, _ ->
                        viewModel.deleteCourseViewModel(args)
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }
    }
}