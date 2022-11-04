package com.example.skillbox_hw_quiz.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentStartBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class StartFragment : ViewBindingFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    private var selectedDate: LocalDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.quizButton.isGone = true
        initToolBar()
        initDateOfBirth()
        navigation()

    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.quiz)
    }

    private fun initDateOfBirth() {
        binding.birthdayDateButton.setOnClickListener {
            if (selectedDate == null) {
                selectedDate = LocalDate.now()
            }
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    Snackbar.make(
                        binding.birthdayDateButton,
                        "Your date of birth is $selectedDate",
                        Snackbar.LENGTH_LONG
                    ).show()
                },
                selectedDate?.year!!,
                selectedDate!!.month.value - 1,
                selectedDate!!.dayOfMonth
            )
            dialog.apply {
                setTitle(R.string.your_birthday)
                show()
            }
            toggleQuizButton()
        }
    }

    private fun toggleQuizButton() {
        val button = binding.quizButton
        val transition: Transition = Fade()
        transition.duration = 3000
        transition.addTarget(button)
        TransitionManager.beginDelayedTransition(binding.root, transition)
        button.isGone = false
    }

    private fun navigation() {
        binding.quizButton.setOnClickListener {
            val directions =
                StartFragmentDirections.actionStartFragmentToQuestionsFragment()
            findNavController().navigate(directions)
        }
    }
}