package com.example.hw_files.files

import androidx.lifecycle.ViewModel
import com.example.hw_files.data.FilesRepository

class FilesViewModel: ViewModel() {

    val repository = FilesRepository()

}