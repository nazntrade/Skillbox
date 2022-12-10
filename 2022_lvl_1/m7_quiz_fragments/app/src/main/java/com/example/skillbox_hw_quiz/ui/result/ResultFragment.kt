package com.example.skillbox_hw_quiz.ui.result

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.skillbox_hw_quiz.R
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
        if (incomingArgs.args.isNotEmpty()) {
            binding.resultFragmentTextView.text = incomingArgs.args
        } else {
            binding.resultFragmentTextView.setText(R.string.you_didn_t_answer_the_questions)
        }
    }

    private fun finishTestAndCloseResults() {
        val directions =
            ResultFragmentDirections.actionResultFragmentToStartFragment()

        binding.finishButton.setOnClickListener {
            findNavController().navigate(directions)
        }

        // to handle back button
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(directions)
        }
    }
}