package com.skillbox.permissions_date_14

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.skillbox.permissions_date_14.R
import kotlinx.android.synthetic.main.fragment_normal_permission.*

class NormalPermissionFragment: Fragment(R.layout.fragment_normal_permission) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadImage.setOnClickListener {
            loadImageFromInternet()
        }
    }

    private fun loadImageFromInternet() {
        Glide.with(this)
            .load("https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-600w-1048185397.jpg")
            .into(internetImageView)
    }

}