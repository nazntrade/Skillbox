package com.example.clientContprovider.contacts.new_course

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.clientContprovider.R
import com.example.clientContprovider.contacts.data.CoursesRepository
import com.example.clientContprovider.contacts.data.IncorrectFormException
import com.example.clientContprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NewCourseViewModel(application: Application) : AndroidViewModel(application) {

    private val courseRepository = CoursesRepository(application)

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    val saveSuccessViewModel: LiveData<Unit>
        get() = saveSuccessLiveEvent

    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    val saveErrorViewModel: LiveData<Int>
        get() = saveErrorLiveEvent

    fun save(title: String) {
        viewModelScope.launch {
            try {
                courseRepository.saveCourse(title)
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when(t) {
                is IncorrectFormException -> R.string.the_form_was_filled_out_incorrectly
                else -> R.string.contact_add_save_error
            }
        )
    }

}