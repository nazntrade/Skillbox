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
    private val answersIndex = mutableListOf<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        initQuestionnaire()
        getResult()
    }

    private fun initToolBar() {
        val toolbar = binding.appBar.toolBar
        toolbar.title = getString(R.string.questions)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun initQuestionnaire() {

        val radioGroup1 = binding.radioGroup1
        val radioGroup2 = binding.radioGroup2
        val radioGroup3 = binding.radioGroup3

        viewModel.loadQuiz()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.quizFlow.collect { quiz ->
                quiz?.let { safeQuiz ->
                    radioGroup1.questionTextView.text = safeQuiz.questions[0].question
                    radioGroup1.radioButton1.text = safeQuiz.questions[0].answers[0]
                    radioGroup1.radioButton2.text = safeQuiz.questions[0].answers[1]
                    radioGroup1.radioButton3.text = safeQuiz.questions[0].answers[2]
                    radioGroup1.radioButton4.text = safeQuiz.questions[0].answers[3]
                    radioGroup2.questionTextView.text = safeQuiz.questions[1].question
                    radioGroup2.radioButton1.text = safeQuiz.questions[1].answers[0]
                    radioGroup2.radioButton2.text = safeQuiz.questions[1].answers[1]
                    radioGroup2.radioButton3.text = safeQuiz.questions[1].answers[2]
                    radioGroup2.radioButton4.text = safeQuiz.questions[1].answers[3]
                    radioGroup3.questionTextView.text = safeQuiz.questions[2].question
                    radioGroup3.radioButton1.text = safeQuiz.questions[2].answers[0]
                    radioGroup3.radioButton2.text = safeQuiz.questions[2].answers[1]
                    radioGroup3.radioButton3.text = safeQuiz.questions[2].answers[2]
                    radioGroup3.radioButton4.text = safeQuiz.questions[2].answers[3]
                }
            }
        }
    }

    private fun getResult() {

        val radioGroup1 = binding.radioGroup1
        val radioGroup2 = binding.radioGroup2
        val radioGroup3 = binding.radioGroup3

        var argsToResult = "a"

        val radioButtonID: Int = radioGroup1.radioGroup.checkedRadioButtonId
        val radioButton: View = radioGroup1.radioGroup.findViewById(radioButtonID)
        val idx: Int = radioGroup1.radioGroup.indexOfChild(radioButton)


        answersIndex.add(idx)
        binding.answerButton.isEnabled = true
        viewModel.getAnswer(answersIndex)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.rationaleFlow.collect { rationale ->
                rationale?.let { safeRationale ->
                    argsToResult = safeRationale
                }
            }
        }

        binding.answerButton.setOnClickListener {
            val directions =
                QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment(
                    argsToResult
                )
            if (argsToResult.isNotEmpty()) {
                findNavController().navigate(directions)
                radioGroup1.radioGroup.clearCheck()
                radioGroup2.radioGroup.clearCheck()
                radioGroup3.radioGroup.clearCheck()
            }
        }
    }
}

