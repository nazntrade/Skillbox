package com.skillbox.m11_timer_data_storage.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m11_timer_data_storage.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app){

    private val repository = Repository()

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