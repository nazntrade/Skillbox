package com.example.skillbox_hw_quiz.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentResultsBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment

class ResultFragment : ViewBindingFragment<FragmentResultsBinding>(FragmentResultsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.toolBar.title = getString(R.string.result)

//        binding.quizButton.setOnClickListener {
//            findNavController().navigate(R.id.action_startFragment_to_questionsFragment)
//        }
    }

}