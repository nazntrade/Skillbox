package com.example.clientContprovider.contacts.courseList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.clientContprovider.contacts.data.Course
import com.example.clientContprovider.contacts.data.CoursesRepository
import kotlinx.coroutines.launch

class CourseListViewModel(application: Application) : AndroidViewModel(application) {

    private val contactsListRepository = CoursesRepository(application)

    private val contactsListMutableLiveData = MutableLiveData<List<Course>>()
    val courseListLiveData: LiveData<List<Course>>
        get() = contactsListMutableLiveData


    fun loadList(){
        viewModelScope.launch {
            try {
                contactsListMutableLiveData.postValue(contactsListRepository.getAllCourses())
            }catch (t: Throwable) {
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }
}