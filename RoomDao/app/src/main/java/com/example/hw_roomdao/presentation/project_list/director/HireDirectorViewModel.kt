package com.example.hw_roomdao.presentation.project_list.director

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.EmployeeRepository
import com.example.hw_roomdao.data.IncorrectFormException
import com.example.hw_roomdao.data.db.models.Director
import com.example.hw_roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class HireDirectorViewModel : ViewModel() {

    private val employeeRepository = EmployeeRepository()

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent


    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    val saveErrorLiveData: LiveData<Int>
        get() = saveErrorLiveEvent


    fun updateDirector(
        directorId: Long,
        directorName: String,
        companyId: Long
    ) {
        val director = Director(
            directorId = directorId,
            directorName = directorName,
            companyId = companyId
        )
        viewModelScope.launch {
            try {
                employeeRepository.updateDirector(director)
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