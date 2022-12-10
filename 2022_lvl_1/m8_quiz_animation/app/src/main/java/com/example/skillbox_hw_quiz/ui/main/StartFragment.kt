package com.example.skillbox_hw_quiz.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentStartBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment

class StartFragment : ViewBindingFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        navigation()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.quiz)
    }

    private fun navigation() {
        binding.quizButton.setOnClickListener {
            val directions =
                StartFragmentDirections.actionStartFragmentToQuestionsFragment()
            findNavController().navigate(directions)
        }
    }
}