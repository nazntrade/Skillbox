package com.example.hw15_viewpager_dialog_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hw15_viewpager_dialog_fragments.databinding.FragmentViewpagerBinding

class ViewPagerFragment : Fragment(R.layout.fragment_viewpager) {

    private lateinit var binding: FragmentViewpagerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewpagerBinding.bind(view)


    }
}