package com.becker.beckerSkillCinema.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentSearchDatePickerBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import java.util.*

class FragmentSearchDatePicker :
    ViewBindingFragment<FragmentSearchDatePickerBinding>(FragmentSearchDatePickerBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.searchSettingsFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rangeFrom.setDateListener = { from = it }
            rangeTo.setDateListener = { to = it }
            rangeFrom.setOnClickListener {
                checkDate()
            }
            rangeTo.setOnClickListener {
                checkDate()
            }
            dataPickerBackBtn.setOnClickListener {
                findNavController().popBackStack(R.id.searchSettingsFragment, false)
            }
            btnDatepickerSelect.setOnClickListener {
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
    }

    private fun checkDate() {
        when (from <= to) {
            true -> binding.btnDatepickerSelect.isEnabled = true
            false -> binding.btnDatepickerSelect.isEnabled = false
        }
    }

    companion object {
        var from = 1850
        var to = Calendar.getInstance().get(Calendar.YEAR)
    }
}