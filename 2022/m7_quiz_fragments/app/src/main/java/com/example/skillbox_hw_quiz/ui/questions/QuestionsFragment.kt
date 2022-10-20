package com.example.skillbox_hw_quiz.ui.questions

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.data.Quiz
import com.example.skillbox_hw_quiz.data.QuizStorage
import com.example.skillbox_hw_quiz.databinding.FragmentQuestionsBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment
import java.util.*

class QuestionsFragment :
    ViewBindingFragment<FragmentQuestionsBinding>(FragmentQuestionsBinding::inflate) {

    lateinit var quiz: Quiz

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
        quiz = when (Locale.getDefault().language) {
            "ru" -> QuizStorage.getQuiz(locale = QuizStorage.Locale.Ru)
            else -> {
                QuizStorage.getQuiz(locale = QuizStorage.Locale.En)
            }
        }
        binding.radioGroup1.questionTextView.text = quiz.questions[0].question
        binding.radioGroup1.radioButton1.text= quiz.questions[0].answers[0]
        binding.radioGroup1.radioButton2.text= quiz.questions[0].answers[1]
        binding.radioGroup1.radioButton3.text= quiz.questions[0].answers[2]
        binding.radioGroup1.radioButton4.text= quiz.questions[0].answers[3]

        binding.radioGroup2.questionTextView.text = quiz.questions[1].question
        binding.radioGroup2.radioButton1.text= quiz.questions[1].answers[0]
        binding.radioGroup2.radioButton2.text= quiz.questions[1].answers[1]
        binding.radioGroup2.radioButton3.text= quiz.questions[1].answers[2]
        binding.radioGroup2.radioButton4.text= quiz.questions[1].answers[3]

        binding.radioGroup3.questionTextView.text = quiz.questions[2].question
        binding.radioGroup3.radioButton1.text= quiz.questions[2].answers[0]
        binding.radioGroup3.radioButton2.text= quiz.questions[2].answers[1]
        binding.radioGroup3.radioButton3.text= quiz.questions[2].answers[2]
        binding.radioGroup3.radioButton4.text= quiz.questions[2].answers[3]

    }

//        binding.quizButton.setOnClickListener {
//            findNavController().navigate(R.id.action_startFragment_to_questionsFragment)
//        }

    private fun showResult() {
        binding.answerButton.setOnClickListener {
            findNavController().navigate(R.id.action_questionsFragment_to_resultFragment)
        }
    }
}
