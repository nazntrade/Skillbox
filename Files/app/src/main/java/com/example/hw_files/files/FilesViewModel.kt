package com.example.hw_files.files

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_files.data.FilesRepository
import kotlinx.coroutines.launch

class FilesViewModel : ViewModel() {

    private val repository = FilesRepository()

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val messageNameLiveData = MutableLiveData<String>()
    val messageName: LiveData<String>
        get() = messageNameLiveData

    private val fileExistsLiveData = MutableLiveData<Boolean>()
    val fileExists: LiveData<Boolean>
        get() = fileExistsLiveData

    fun downloadFile(link: String, requireContext: Context) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                repository.downloadFile(link, requireContext)
                    //херня с уведомлениями
                messageNameLiveData.postValue(repository.getFileName(link))// тут не верно. не нужно выводить сообщ, есля я ничего не делал
                fileExistsLiveData.postValue(repository.fileExists)
            } catch (t: Throwable) {

            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}