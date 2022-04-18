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

    fun downloadFile(link: String, requireContext: Context) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                repository.downloadFile(link, requireContext)
                messageNameLiveData.postValue(repository.getFileName(link))
            } catch (t: Throwable) {

            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}