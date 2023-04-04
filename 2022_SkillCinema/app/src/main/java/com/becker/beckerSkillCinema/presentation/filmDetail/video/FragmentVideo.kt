package com.becker.beckerSkillCinema.presentation.filmDetail.video

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.becker.beckerSkillCinema.databinding.FragmentVideoBinding
import com.becker.beckerSkillCinema.presentation.MainActivity
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment

class FragmentVideo : ViewBindingFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // if your webview can go back it will go back
                if (binding.webView.canGoBack()) binding.webView.goBack()
                // if your webview cannot go back
                // it will go back to previous backStack
                else findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val incomeArgs: FragmentVideoArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeFullScreen()

        val webView = binding.webView
        val url = "https://www.youtube.com/results?search_query=${incomeArgs.filmName}+trailer"

        webView.apply {
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = true
            webViewClient = WebViewClient()

            webChromeClient = object : WebChromeClient() {
                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    super.onShowCustomView(view, callback)
                    binding.apply {
                        webView.visibility = View.GONE
                        customView.visibility = View.VISIBLE
                        customView.addView(view)
                    }
                }

                override fun onHideCustomView() {
                    super.onHideCustomView()
                    binding.apply {
                        webView.visibility = View.VISIBLE
                        customView.visibility = View.GONE
                    }
                }
            }
            loadUrl(url)
        }
    }

    private fun makeFullScreen() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            (requireActivity() as MainActivity).window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            (requireActivity() as MainActivity).window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onPause() {
        super.onPause()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            (requireActivity() as MainActivity).window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            (requireActivity() as MainActivity).window.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onResume() {
        super.onResume()
        makeFullScreen()
    }
}
