package com.becker.beckerSkillCinema.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentSearchDatePickerBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment

class FragmentSearchDatePicker : ViewBindingFragment<FragmentSearchDatePickerBinding>(FragmentSearchDatePickerBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.searchSettingsFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rangeFrom.setDateListener = { from = it }
        binding.rangeTo.setDateListener = { to = it }

        binding.rangeFrom.setOnClickListener {
            checkDate()
        }
        binding.rangeTo.setOnClickListener {
            checkDate()
        }

        binding.btDatepickerBack.setOnClickListener {
            findNavController().navigate(R.id.searchSettingsFragment)
        }

        binding.btDatepickerSelect.setOnClickListener {
            if (from <= to) {
                val action =
                    FragmentSearchDatePickerDirections
                        .actionFragmentSearchDatePickerToSearchSettingsFragment(
                        yearFrom = from.toString(), yearTo = to.toString()
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun checkDate() {
        when (from <= to) {
            true -> binding.btDatepickerSelect.isEnabled = true
            false -> binding.btDatepickerSelect.isEnabled = false
        }

    }

    companion object {
        var from = -1
        var to = -2
    }
}