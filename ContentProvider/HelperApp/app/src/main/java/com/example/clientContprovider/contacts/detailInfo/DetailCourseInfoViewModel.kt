package com.example.clientContprovider.contacts.detailInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.clientContprovider.contacts.data.CoursesRepository
import com.example.clientContprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailCourseInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoursesRepository(application)

    private val deleteSuccessLiveEvent = SingleLiveEvent<Unit>()
    val deleteSuccessLiveData: LiveData<Unit>
        get() = deleteSuccessLiveEvent

    fun deleteCourseViewModel(args: DetailCourseInfoFragmentArgs) {
        viewModelScope.launch {
            try {
                repository.deleteCourse(args)
                deleteSuccessLiveEvent.postValue(Unit)
            }catch (t: Throwable){}
        }
    }
}