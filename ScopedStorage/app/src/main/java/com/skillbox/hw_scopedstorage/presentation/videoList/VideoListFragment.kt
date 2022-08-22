package com.skillbox.hw_scopedstorage.presentation.videoList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.databinding.FragmentVideoListBinding
import com.skillbox.hw_scopedstorage.utils.ViewBindingFragment

//since this time binding only this
class VideoListFragment :
    ViewBindingFragment<FragmentVideoListBinding>(FragmentVideoListBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()

    }


    //including statusBar
    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolBarTitle)
    }

}