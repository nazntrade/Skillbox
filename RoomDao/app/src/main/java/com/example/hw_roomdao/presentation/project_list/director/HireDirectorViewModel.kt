package com.example.hw_roomdao.presentation.project_list.director

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.IncorrectFormException
import com.example.hw_roomdao.data.db.models.Director
import kotlinx.coroutines.launch
import timber.log.Timber

class HireDirectorViewModel: ViewModel() {

    fun saveDirector(
        directorId: Long,
        directorName: String,
    ) {

        val director = Director(
            directorId = directorId,
            directorName = directorName,
        )

        viewModelScope.launch {
            try {
                if (directorId == 0L) {
                    directorRepository.saveEmployee(director)
                } else {
                    directorRepository.updateEmployee(director)
                }
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t, "user save error")
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when (t) {
                is IncorrectFormException -> R.string.user_add_error_incorrect_format
                else -> R.string.user_add_error_default
            }
        )
    }

}