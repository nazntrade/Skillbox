package com.example.clientContprovider.contacts.detailInfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.clientContprovider.R
import com.example.clientContprovider.databinding.FragmentDetailInfoBinding
import kotlin.getValue


class DetailCourseInfoFragment : Fragment(R.layout.fragment_detail_info) {

    private val viewModel by viewModels<DetailCourseInfoViewModel>()
    private val args: DetailCourseInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailInfoBinding.bind(requireView())

        binding.detailNameTextView.text = args.currentContact.title

        bindViewModel()
    }

    private fun bindViewModel() {
        binding.deleteButton.setOnClickListener { viewModel.deleteCourseViewModel(args) }
        viewModel.deleteSuccessLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

}