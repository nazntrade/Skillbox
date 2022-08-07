package com.example.hw_roomdao.presentation.project_list.director

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw_roomdao.R
import com.example.hw_roomdao.databinding.FragmentAddDirectorBinding
import com.example.hw_roomdao.utils.toast

class HireDirectorFragment: Fragment(R.layout.fragment_add_director) {

    private lateinit var binding: FragmentAddDirectorBinding

    private val viewModel by viewModels<HireDirectorViewModel>()
    private val args: HireDirectorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddDirectorBinding.bind(view)

        initToolBar()
        bindViewModel()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = "Hire director"
        binding.appBar.toolBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViewModel(){
        binding.saveButton.setOnClickListener{
            viewModel.updateDirector(
                args.argsDirector.directorId,
                directorName = binding.addDirectorTextField.editText?.text?.toString().orEmpty(),
                args.argsDirector.companyId
            )
        }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

}