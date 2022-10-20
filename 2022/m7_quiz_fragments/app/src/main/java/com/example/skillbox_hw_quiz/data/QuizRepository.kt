package com.example.skillbox_hw_quiz.data

import android.content.Context
import java.util.*

class QuizRepository(private val context: Context) {

    val quiz = when (Locale.getDefault().language) {
        "ru" -> QuizStorage.getQuiz(locale = QuizStorage.Locale.Ru)
        else -> {
            QuizStorage.getQuiz(locale = QuizStorage.Locale.En)
        }
    }

}