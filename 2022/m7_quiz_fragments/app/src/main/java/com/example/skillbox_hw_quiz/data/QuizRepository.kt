package com.example.skillbox_hw_quiz.data

import java.util.*

class QuizRepository {

    val quiz = when (Locale.getDefault().language) {
        "ru" -> QuizStorage.getQuiz(locale = QuizStorage.Locale.Ru)
        else -> {
            QuizStorage.getQuiz(locale = QuizStorage.Locale.En)
        }
    }

    fun getRationale(answers: List<Int>): String {
        return QuizStorage.answer(quiz, answers)
    }
}