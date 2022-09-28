package com.skillbox.hw_scopedstorage.presentation.addVideo

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skillbox.hw_scopedstorage.databinding.DialogAddVideoBinding
import com.skillbox.hw_scopedstorage.utils.toast


//
// BottomSheetDialogFragment() for popup View on main screen
//
// and binding without utils: abstract class ViewBindingFragment<T : ViewBinding>
//
class AddVideoDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: AddVideoViewModel by viewModels()

    //select dir
    private lateinit var selectVideoDirectoryLauncher: ActivityResultLauncher<String>

    private var _binding: DialogAddVideoBinding? = null
    private val binding: DialogAddVideoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddVideoBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.isEnabled = false
        binding.saveCustomDirButton.isEnabled = false
        bindViewModel()

        //select dir
        initSelectVideoFolderLauncher()
    }

    //select dir. this f call picker and return selected uri
    private fun initSelectVideoFolderLauncher() {
        selectVideoDirectoryLauncher = registerForActivityResult(
            CreateDocument("video/mp4")
        ) { uri ->
            handleSelectDirectory(uri)
        }
    }

    //select dir
    private fun handleSelectDirectory(customUri: Uri?) {
        if (customUri == null) {
            toast("directory not selected")
            return
        }
        val url = binding.urlTextField.editText?.text?.toString().orEmpty()
        viewModel.saveVideoToCustomDir(url, customUri)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun bindViewModel() {

        binding.urlTextField.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) {
                    binding.saveButton.isEnabled = true
                    binding.saveCustomDirButton.isEnabled = true
                }
            }
        })

//select dir
        binding.saveCustomDirButton.setOnClickListener {
            val name = binding.nameTextField.editText?.text?.toString().orEmpty()
            selectVideoDirectoryLauncher.launch(name)
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameTextField.editText?.text?.toString().orEmpty()
            val url = binding.urlTextField.editText?.text?.toString().orEmpty()
            viewModel.saveVideo(name, url)
        }

        binding.saveSampleVideoButton.setOnClickListener {
            viewModel.saveVideo(
                viewModel.sampleVideoNamesViewModel.random() + (2..100).random(),
                viewModel.sampleVideoLinksViewModel.random()
            )
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