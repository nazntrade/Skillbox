package com.skillbox.m11_timer_data_storage.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skillbox.m11_timer_data_storage.databinding.FragmentMainBinding
import com.skillbox.m11_timer_data_storage.ui.ViewBindingFragment

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveData()
        clearData()
        getDataFromStorage()
    }

    private fun saveData() {
        binding.saveButton.setOnClickListener {
            val text = binding.editTextField.text.toString()
            viewModel.saveData(text)
        }
    }

    private fun getDataFromStorage() {
        val textField = binding.savedDataTextView
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dataFromStorageFlow.collect { text ->
                if (text != null) {
                    textField.text = text
                } else {
                    textField.text = viewModel.getDataSharedPref()
                }
            }
        }
    }

    private fun clearData() {
        binding.clearButton.setOnClickListener {
            viewModel.clearText()
            binding.editTextField.text = null
        }
    }
}