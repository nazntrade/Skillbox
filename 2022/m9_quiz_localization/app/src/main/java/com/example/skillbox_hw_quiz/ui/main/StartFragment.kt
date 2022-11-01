package com.example.skillbox_hw_quiz.ui.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.time.LocalDateTime
import java.time.ZoneId

class StartFragment : ViewBindingFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.quizButton.isGone= true
        initToolBar()
        initDateOfBirth()
        navigation()

    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.quiz)
    }

    private fun initDateOfBirth() {
        binding.birthdayDateButton.setOnClickListener {
            val currentDateTime = LocalDateTime.now()
            DatePickerDialog(
                requireContext(), { _, year, month, dayOfMonth ->
                    TimePickerDialog(
                        requireContext(), { _, hourOfDay, minute ->
                            val zonedDateTime =
                                LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                    .atZone(ZoneId.systemDefault())
                            selectedMessageInstant = zonedDateTime.toInstant()
                            dateLocationMessages[position].createdAt =
                                selectedMessageInstant ?: Instant.now()
                            dateLocationMessageAdapter?.notifyItemChanged(position)
                        },
                        currentDateTime.hour,
                        currentDateTime.minute,
                        true
                    ).show()
                },
                currentDateTime.year,
                currentDateTime.month.value - 1,
                currentDateTime.dayOfMonth
            ).show()
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