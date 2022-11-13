package com.skillbox.m16_architecture.presetation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skillbox.m16_architecture.R
import com.skillbox.m16_architecture.databinding.FragmentBoredBinding

class BoredFragment : ViewBindingFragment<FragmentBoredBinding>(FragmentBoredBinding::inflate) {

    private val viewModel: BoredViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        getUsefulActivity()
        printUsefulActivity()
    }

    private fun initToolbar(){
        binding.appBar.toolBar.title = getString(R.string.got_bored_click_the_button)
    }

    private fun getUsefulActivity() {
        binding.button.setOnClickListener {
            viewModel.getUsefulActivity()
        }
    }

    private fun printUsefulActivity() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.usefulActivityStateFlow.collect { usefulActivity ->
                binding.textView.text = usefulActivity?.activity
            }
        }
    }
}