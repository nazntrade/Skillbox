package com.example.hw_files.files

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw_files.R
import com.example.hw_files.databinding.FragmentFileBinding

class FilesFragment : Fragment(R.layout.fragment_file) {

    private lateinit var binding: FragmentFileBinding
    private val viewModel: FilesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFileBinding.bind(view)

        downloadFile()
        bindViewModel()
        downloadAssetsFiles()
        playSounds()
    }

    private fun playSounds() {
        binding.soundButton.setOnClickListener {
            MediaPlayer.create(requireContext(), R.raw.wagner).start()
        }
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

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhileDownload)
        viewModel.fileExistsOrDownloaded.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            MediaPlayer.create(requireContext(), R.raw.retro_game_notification).start()
        }
    }

    private fun doWhileDownload(isLoading: Boolean) {
        binding.editTextField.isEnabled = isLoading.not()
        binding.downloadButton.isEnabled = isLoading.not()
        binding.progressBar.isGone = isLoading.not()
    }
}
