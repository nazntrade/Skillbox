package com.example.skillbox_hw_quiz.ui.questions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentQuestionsBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment

class QuestionsFragment :
    ViewBindingFragment<FragmentQuestionsBinding>(FragmentQuestionsBinding::inflate) {

    private val viewModel: QuestionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        initQuestionnaire()
        showResult()
    }

    private fun initToolBar() {
        val toolbar = binding.appBar.toolBar
        toolbar.title = getString(R.string.questions)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun initQuestionnaire() {

        viewModel.loadQuiz()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.quizFlow.collect { quiz ->
                quiz?.let { safeQuiz ->
                    binding.radioGroup1.questionTextView.text = safeQuiz.questions[0].question
                    binding.radioGroup1.radioButton1.text = safeQuiz.questions[0].answers[0]
                    binding.radioGroup1.radioButton2.text = safeQuiz.questions[0].answers[1]
                    binding.radioGroup1.radioButton3.text = safeQuiz.questions[0].answers[2]
                    binding.radioGroup1.radioButton4.text = safeQuiz.questions[0].answers[3]

                    binding.radioGroup2.questionTextView.text = safeQuiz.questions[1].question
                    binding.radioGroup2.radioButton1.text = safeQuiz.questions[1].answers[0]
                    binding.radioGroup2.radioButton2.text = safeQuiz.questions[1].answers[1]
                    binding.radioGroup2.radioButton3.text = safeQuiz.questions[1].answers[2]
                    binding.radioGroup2.radioButton4.text = safeQuiz.questions[1].answers[3]

                    binding.radioGroup3.questionTextView.text = safeQuiz.questions[2].question
                    binding.radioGroup3.radioButton1.text = safeQuiz.questions[2].answers[0]
                    binding.radioGroup3.radioButton2.text = safeQuiz.questions[2].answers[1]
                    binding.radioGroup3.radioButton3.text = safeQuiz.questions[2].answers[2]
                    binding.radioGroup3.radioButton4.text = safeQuiz.questions[2].answers[3]

                }
            }
        }
    }

//        binding.quizButton.setOnClickListener {
//            findNavController().navigate(R.id.action_startFragment_to_questionsFragment)
//        }

    private fun showResult() {
        binding.answerButton.setOnClickListener {
            val directions =
                QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment()
            findNavController().navigate(directions)
        }
    }
}
