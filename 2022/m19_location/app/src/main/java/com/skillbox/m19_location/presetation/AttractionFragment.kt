package com.skillbox.m19_location.presetation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skillbox.m19_location.databinding.FragmentAttractionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AttractionFragment : ViewBindingFragment<FragmentAttractionBinding>(FragmentAttractionBinding::inflate) {

    @Inject
    lateinit var attractionViewModelFactory: AttractionViewModelFactory
    
    private val viewModel: AttractionViewModel by viewModels {
        attractionViewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        getAttractions()
        printUsefulActivity()
    }

    private fun initToolbar() {
//        binding.appBar.toolBar.title = getString(R.string.got_bored_click_the_button)
    }

    private fun getAttractions() {
        binding.getAttractions.setOnClickListener {
            viewModel.getAttractions()
        }
    }

    private fun printUsefulActivity() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.attractionsFlow.collect { usefulActivityText ->
//                binding.textView.text = usefulActivityText
            }
        }
    }
}