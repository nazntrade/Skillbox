package com.example.skillbox_hw_quiz.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.data.Quiz
import com.example.skillbox_hw_quiz.databinding.FragmentResultsBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment

class ResultFragment :
    ViewBindingFragment<FragmentResultsBinding>(FragmentResultsBinding::inflate) {

    private val incomingArgs: ResultFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        initAnswers()
        finishTestAndCloseResults()
    }

    private fun initToolBar() {
        val toolbar = binding.appBar.toolBar
        toolbar.title = getString(R.string.result)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun initAnswers() {
        binding.resultFragmentTextView.text = incomingArgs.args
    }

    private fun finishTestAndCloseResults() {
        binding.finishButton.setOnClickListener {
            val directions =
                ResultFragmentDirections.actionResultFragmentToStartFragment()
            findNavController().navigate(directions)
        }
    }
}