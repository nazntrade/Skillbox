package com.skillbox.m16_architecture.presetation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skillbox.m16_architecture.databinding.FragmentBoredBinding

class BoredFragment : ViewBindingFragment<FragmentBoredBinding>(FragmentBoredBinding::inflate) {

    private val viewModel: BoredViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEntertainment()
        writeEntertainment()
    }

    private fun getEntertainment() {
        binding.button.setOnClickListener {
            viewModel.getEntertainment()
        }
    }

    private fun writeEntertainment() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.entertainmentStateFlow.collect { entertainment ->
                binding.textView.text = entertainment?.activity
            }
        }
    }
}