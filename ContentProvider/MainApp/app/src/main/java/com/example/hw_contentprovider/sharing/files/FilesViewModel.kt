package com.example.hw_contentprovider.sharing.files

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_contentprovider.sharing.data.FilesRepository
import kotlinx.coroutines.launch

class FilesViewModel : ViewModel() {

    private val repository = FilesRepository()

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val fileExistsOrDownloadedLiveData = MutableLiveData<String>()
    val fileExistsOrDownloaded: LiveData<String>
        get() = fileExistsOrDownloadedLiveData

    fun shareFiles() {
        viewModelScope.launch {
            try {
                repository.shareFiles()
            }catch (t: Throwable) {
            }
        }
    }

    fun downloadAssetsFiles(requireContext: Context, resources: Resources) {
        viewModelScope.launch {
            try {
                repository.downloadAssetsFiles(requireContext, resources)
            } catch (t: Throwable) {
            }
        }
    }

    fun downloadFile(link: String, requireContext: Context) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                repository.downloadFile(link, requireContext)
            } catch (t: Throwable) {

            } finally {
                isLoadingLiveData.postValue(false)
                fileExistsOrDownloadedLiveData.postValue(repository.fileExistsOrDownloaded)
            }
        }
    }
}