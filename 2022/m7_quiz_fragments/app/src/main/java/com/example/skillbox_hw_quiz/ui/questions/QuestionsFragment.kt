package com.example.skillbox_hw_quiz.ui.questions

import android.os.Bundle
import android.view.View
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.data.QuizStorage
import com.example.skillbox_hw_quiz.databinding.FragmentQuestionsBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment

class QuestionsFragment :
    ViewBindingFragment<FragmentQuestionsBinding>(FragmentQuestionsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        val question = QuizStorage.getQuiz(locale = QuizStorage.Locale.En)
        binding.textView.text = question.toString()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.questions)
    }

//        binding.quizButton.setOnClickListener {
//            findNavController().navigate(R.id.action_startFragment_to_questionsFragment)
//        }

}
