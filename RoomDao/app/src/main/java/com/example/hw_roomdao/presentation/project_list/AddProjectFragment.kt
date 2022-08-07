package com.example.hw_roomdao.presentation.project_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.databinding.FragmentProjectAddBinding
import com.example.hw_roomdao.utils.hideKeyboardAndClearFocus
import com.example.hw_roomdao.utils.toast

class AddProjectFragment : Fragment(R.layout.fragment_project_add) {

    private lateinit var binding: FragmentProjectAddBinding

    private val viewModel by viewModels<AddProjectViewModel>()

    private val args: AddProjectFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProjectAddBinding.bind(view)

        initToolbar()
        bindViewModel()
    }

    private fun initToolbar() {
        binding.appBar.toolBar.setTitle(R.string.add_new_project_title)
        binding.appBar.toolBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViewModel() {
        binding.saveButton.setOnClickListener { saveProject() }
        viewModel.existingProjectLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                setExistingProjectInfo(it)
            }
        }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun saveProject() {
        viewModel.save(
            title = binding.addTitleTextField.editText?.text?.toString().orEmpty(),
            companyId = args.currentCompany.companyId
        )
    }

    private fun setExistingProjectInfo(project: Project?) {
        if (project != null) {
            binding.addTitleTextField.editText?.setText(project.title)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}