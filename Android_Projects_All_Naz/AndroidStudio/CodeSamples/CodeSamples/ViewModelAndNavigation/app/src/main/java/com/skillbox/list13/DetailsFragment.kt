package com.skillbox.list13

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment: Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        idTextView.text = args.personId.toString()
    }

}