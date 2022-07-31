package com.example.hw_roomdao.presentation.project_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.IncorrectFormException
import com.example.hw_roomdao.data.ProjectRepository
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class AddProjectViewModel : ViewModel() {

    private val projectRepository = ProjectRepository()

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    private val existingProjectMutableLiveData = MutableLiveData<Project>()

    val existingProjectLiveData: LiveData<Project?>
        get() = existingProjectMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val saveErrorLiveData: LiveData<Int>
        get() = saveErrorLiveEvent

    fun init(id: Long) {
//        viewModelScope.launch {
//            try {
//                val project = projectRepository.getEmployeeById(id)
//                existingProjectMutableLiveData.postValue(project)
//            } catch (t: Throwable) {
//                Timber.e(t)
//            }
//        }
    }

    fun save(
        id: Long,
        title: String,
    ) {

        val project = Project(
            id = id,
            title = title
        )

        viewModelScope.launch {
            try {
                if (id == 0L) {
                    projectRepository.saveProject(project)
                } else {
                    projectRepository.updateProject(project)
                }
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t, "project save error")
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when (t) {
                is IncorrectFormException -> R.string.user_add_error_incorrect_format
                else -> R.string.project_add_error_default
            }
        )
    }
}