package com.example.skillbox_hw_quiz.ui.result

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
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
        animateToolBar(toolbar)
    }

    private fun animateToolBar(toolbar: Toolbar) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_toolbar)
        animation.reset()
        // Repeat animation infinitely
        animation.repeatCount = Animation.INFINITE
        // Reverse animation at the end so the button will fade back in
        animation.repeatMode = Animation.REVERSE
        toolbar.clearAnimation()
        toolbar.startAnimation(animation)
    }

    private fun initAnswers() {
        if (incomingArgs.args.isNotEmpty()) {
            binding.resultFragmentTextView.text = incomingArgs.args
        } else {
            binding.resultFragmentTextView.setText(R.string.you_didn_t_answer_the_questions)
        }
    }

    private fun finishTestAndCloseResults() {
        val finishButton = binding.finishButton
        animateButton(finishButton)

        val directions =
            ResultFragmentDirections.actionResultFragmentToStartFragment()

        finishButton.setOnClickListener {
            findNavController().navigate(directions)
        }

        // to handle back button
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(directions)
        }
    }

    private fun animateButton(finishButton: AppCompatButton) {
        val animation: Animation =
            AlphaAnimation(1f, 0f) // Change alpha from fully visible to invisible
        animation.duration = 500 // duration - half a second
        // do not alter animation rate
        animation.interpolator = LinearInterpolator()
        // Repeat animation infinitely
        animation.repeatCount = Animation.INFINITE
        // Reverse animation at the end so the button will fade back in
        animation.repeatMode = Animation.REVERSE
        finishButton.startAnimation(animation)
    }
}