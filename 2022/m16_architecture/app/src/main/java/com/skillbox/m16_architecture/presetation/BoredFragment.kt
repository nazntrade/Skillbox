package com.skillbox.m16_architecture.presetation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skillbox.m16_architecture.R
import com.skillbox.m16_architecture.databinding.FragmentBoredBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BoredFragment : ViewBindingFragment<FragmentBoredBinding>(FragmentBoredBinding::inflate) {

    @Inject
    lateinit var boredViewModelFactory: BoredViewModelFactory
    private val viewModel: BoredViewModel by viewModels {
        boredViewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        getUsefulActivity()
        printUsefulActivity()
    }

    private fun initToolbar() {
        binding.appBar.toolBar.title = getString(R.string.got_bored_click_the_button)
    }

    private fun getUsefulActivity() {
        binding.button.setOnClickListener {
            viewModel.getUsefulActivity()
        }
    }

    private fun printUsefulActivity() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.usefulActivityTextFlow.collect { usefulActivityText ->
                binding.textView.text = usefulActivityText
            }
        }
    }
}