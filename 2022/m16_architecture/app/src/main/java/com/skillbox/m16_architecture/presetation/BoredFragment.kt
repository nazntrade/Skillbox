package com.skillbox.m16_architecture.presetation

import androidx.fragment.app.viewModels
import com.skillbox.m16_architecture.databinding.FragmentBoredBinding

class BoredFragment : ViewBindingFragment<FragmentBoredBinding>(FragmentBoredBinding::inflate) {

    private val viewModel: BoredViewModel by viewModels()

}