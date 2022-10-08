package com.example.lessons14_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lessons14_fragment.databinding.ActivityMainBinding
import com.example.lessons14_fragment.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        binding.inputTextView.text = requireArguments().getString(KEY_TEXT)

    }

    companion object {
        private const val KEY_TEXT = "key text"

        fun newInstance(text: String): InfoFragment {
            return InfoFragment().withArguments {
                putString(KEY_TEXT, text)
            }
        }
    }

}