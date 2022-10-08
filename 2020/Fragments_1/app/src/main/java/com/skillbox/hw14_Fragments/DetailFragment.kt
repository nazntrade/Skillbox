package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        binding.textViewDetail.text = requireArguments().getString(KEY_TEXT)
    }

    companion object {
        private const val KEY_TEXT = "key text"

        fun newInstance(text: String): DetailFragment {
            return DetailFragment().withArguments {
                putString(KEY_TEXT, text)
            }
        }
    }
}