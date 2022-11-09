package com.skillbox.m11_timer_data_storage.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.m11_timer_data_storage.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = Repository(app)

    private val _dataFromStorageFlow = MutableStateFlow<String?>(null)
    val dataFromStorageFlow = _dataFromStorageFlow.asStateFlow()

    fun saveData(text: String) {
        viewModelScope.launch {
            try {
                repository.saveText(text)
                getDataFlow()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    suspend fun getDataSharedPref(): String? {
        return repository.getText()
    }

    fun clearText() {
        viewModelScope.launch {
            try {
                _dataFromStorageFlow.value = repository.clearText()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    private fun getDataFlow() {
        viewModelScope.launch {
            try {
                _dataFromStorageFlow.value = repository.getText()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}