package com.example.hw_files.files

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw_files.R
import com.example.hw_files.databinding.FragmentFileBinding

class FilesFragment : Fragment(R.layout.fragment_file) {

    lateinit var binding: FragmentFileBinding
    private val viewModel: FilesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFileBinding.bind(view)

        downloadFile()
    }

    private fun downloadFile() {
        binding.downloadButton.setOnClickListener {
            viewModel.downloadFile(binding.editTextField.text.toString(), requireContext())
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhileDownload)
        viewModel.messageName.observe(viewLifecycleOwner) {
            Toast.makeText(context, "File: $it has been downloaded", Toast.LENGTH_LONG).show()
        }
    }

    private fun doWhileDownload(isLoading: Boolean) {
        binding.editTextField.isEnabled = isLoading.not()
        binding.downloadButton.isEnabled = isLoading.not()
        binding.progressBar.isGone = isLoading.not()
    }
}
