package com.skillbox.hw_scopedstorage.presentation.playVideo

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.navigation.fragment.navArgs
import com.skillbox.hw_scopedstorage.databinding.FragmentPlayVideoBinding
import com.skillbox.hw_scopedstorage.utils.ViewBindingFragment

class PlayVideoFragment :
    ViewBindingFragment<FragmentPlayVideoBinding>(FragmentPlayVideoBinding::inflate) {

    private val incomingArgs: PlayVideoFragmentArgs by navArgs()

    // declaring a null variable for VideoView
    private var simpleVideoView: VideoView? = null

    // declaring a null variable for MediaController
    private var mediaControls: MediaController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUri = incomingArgs.clickedVideo.uri

        simpleVideoView = binding.simpleVideoView

        playVideoFromUri(videoUri)

    }

    private fun playVideoFromUri(videoUri: Uri) {
        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(context)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.simpleVideoView)
        }

        // set the media controller for video view
        simpleVideoView!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        simpleVideoView!!.setVideoURI(videoUri)

        simpleVideoView!!.requestFocus()

        // starting the video
        simpleVideoView!!.start()

    }
}