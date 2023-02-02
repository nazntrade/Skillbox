package com.becker.beckerSkillCinema.presentation.filmDetail.video

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
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
                // if your webview can go back it will go back
                if (binding.webView.canGoBack())
                    binding.webView.goBack()
                // if your webview cannot go back
                // it will exit the application
                else findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val incomeArgs: FragmentVideoArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoView = binding.webView
        videoView.settings.builtInZoomControls = true
        videoView.webViewClient = WebViewClient()
        val url =
            "https://www.google.ru/search?q=${incomeArgs.filmName}+trailer&newwindow=1&biw=1745&bih=888&tbm=vid&sxsrf=AJOqlzUmuPM0ofjPRoMU7at9l0gHIl6VrA%3A1675324899092&ei=423bY9qaBa6K9u8P6ZyD4AE&ved=0ahUKEwiamPnLr_b8AhUuhf0HHWnOABwQ4dUDCA0&uact=5&oq=%D0%B0%D0%B2%D0%B0%D1%82%D0%B0%D1%80+trailer&gs_lcp=Cg1nd3Mtd2l6LXZpZGVvEAMyBAgjECcyBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB5QAFgAYKYJaABwAHgAgAGhAYgBoQGSAQMwLjGYAQCgAQKgAQHAAQE&sclient=gws-wiz-video"
        videoView.loadUrl(url)
        videoView.settings.javaScriptEnabled = true
        videoView.settings.setSupportZoom(true)
    }
}
