package com.example.hw_contentprovider.sharing.files

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentShareFileBinding

class FilesShareFragment : Fragment(R.layout.fragment_share_file) {

    private lateinit var binding: FragmentShareFileBinding
    private val viewModel: FilesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShareFileBinding.bind(view)

        binding.shareButton.isEnabled = false
        downloadAssetsFiles()
        downloadFile()
        shareFiles()
        bindViewModel()
    }

    private fun downloadAssetsFiles() {
        viewModel.downloadAssetsFiles(requireContext(), resources)
    }

    private fun downloadFile() {
        binding.downloadButton.setOnClickListener {
            if (binding.editTextField.text.isNotEmpty()) {
                viewModel.downloadFile(binding.editTextField.text.toString(), requireContext())
            }
        }
    }

    private fun shareFiles() {
        binding.shareButton.setOnClickListener {
            viewModel.shareFiles()
        }
    }

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhileDownload)
        viewModel.fileExistsOrDownloaded.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            MediaPlayer.create(requireContext(), R.raw.retro_game_notification).start()
            binding.shareButton.isEnabled = true
        }
        viewModel.shareIntent.observe(viewLifecycleOwner) {
            startActivity(it)
        }
    }

    private fun doWhileDownload(isLoading: Boolean) {
        binding.editTextField.isEnabled = isLoading.not()
        binding.downloadButton.isEnabled = isLoading.not()
        binding.progressBar.isGone = isLoading.not()
    }
}
