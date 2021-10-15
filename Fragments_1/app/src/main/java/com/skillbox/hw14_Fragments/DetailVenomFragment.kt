package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.VenomfragmentDetailBinding

class DetailVenomFragment : Fragment(R.layout.venomfragment_detail) {

    private lateinit var binding: VenomfragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = VenomfragmentDetailBinding.bind(view)


    }

}