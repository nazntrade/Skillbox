package com.becker.beckerSkillCinema.presentation.filmDetail.video

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.becker.beckerSkillCinema.databinding.FragmentVideoBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment

class FragmentVideo : ViewBindingFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val incomeArgs: FragmentVideoArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoView = binding.videoView
        val uri: Uri = Uri.parse(incomeArgs.link)
        videoView.setVideoURI(uri)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()

    }
}