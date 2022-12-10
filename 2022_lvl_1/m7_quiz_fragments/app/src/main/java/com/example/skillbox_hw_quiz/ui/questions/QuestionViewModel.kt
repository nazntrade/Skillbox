package com.example.skillbox_hw_quiz.ui.questions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillbox_hw_quiz.data.Quiz
import com.example.skillbox_hw_quiz.data.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class QuestionViewModel(app: Application) : AndroidViewModel(app) {

    private val quizRepository = QuizRepository()

    private val _quizFlow = MutableStateFlow<Quiz?>(null)
    val quizFlow = _quizFlow.asStateFlow()

    fun loadQuiz() {
        viewModelScope.launch {
            try {
                val quiz = quizRepository.quiz
                _quizFlow.value = quiz
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun getAnswers(answers: List<Int>): String {
        return quizRepository.getRationale(answers)
    }
}


