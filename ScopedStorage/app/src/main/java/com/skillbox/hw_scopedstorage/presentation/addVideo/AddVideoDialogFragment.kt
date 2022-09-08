package com.skillbox.hw_scopedstorage.presentation.addVideo

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.databinding.DialogAddVideoBinding
import com.skillbox.hw_scopedstorage.utils.toast

//
// BottomSheetDialogFragment() for popup View on main screen
//
// and binding without utils: abstract class ViewBindingFragment<T : ViewBinding>
//
class AddVideoDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: AddVideoViewModel by viewModels()

    private var _binding: DialogAddVideoBinding? = null
    private val binding: DialogAddVideoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddVideoBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
    }

    private fun bindViewModel() {
        binding.saveButton.setOnClickListener {
            val url = binding.urlTextField.editText?.text?.toString().orEmpty()
            val name = binding.nameTextField.editText?.text?.toString().orEmpty()
            viewModel.saveVideo(name, url)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::setLoading)
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { dismiss() }

    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.contentGroup.isVisible = isLoading.not()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}