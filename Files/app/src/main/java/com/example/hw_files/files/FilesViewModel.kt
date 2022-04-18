package com.example.hw_files.files

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_files.data.FilesRepository
import kotlinx.coroutines.launch

class FilesViewModel : ViewModel() {

    private val repository = FilesRepository()

    fun downloadFile(link: String, requireContext: Context) {
        viewModelScope.launch {
            try {
                repository.downloadFile(link, requireContext)

            } catch (t: Throwable) {

            } finally {

            }
        }
    }
}