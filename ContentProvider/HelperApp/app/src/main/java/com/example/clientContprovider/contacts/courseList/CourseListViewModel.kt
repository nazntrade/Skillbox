package com.example.clientContprovider.contacts.courseList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.clientContprovider.contacts.data.Course
import com.example.clientContprovider.contacts.data.CoursesRepository
import com.example.clientContprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class CourseListViewModel(application: Application) : AndroidViewModel(application) {

    private val coursesRepository = CoursesRepository(application)

    private val courseListMutableLiveData = MutableLiveData<List<Course>>()
    val courseListLiveData: LiveData<List<Course>>
        get() = courseListMutableLiveData

    private val deleteSuccessLiveEvent = SingleLiveEvent<Unit>()
    val deleteSuccessLiveData: LiveData<Unit>
        get() = deleteSuccessLiveEvent


    fun loadList() {
        viewModelScope.launch {
            try {
                courseListMutableLiveData.postValue(coursesRepository.getAllCourses())
            } catch (t: Throwable) {
                courseListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun deleteAllCourses() {
        viewModelScope.launch {
            try {
                coursesRepository.deleteAllCourse()
                deleteSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
            }
        }
    }
}