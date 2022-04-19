package com.example.hw_files.data

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class FilesRepository {

    var fileExists = false

    //https://gitlab.skillbox.ru/learning_materials/android_basic/-/raw/master/README.md
    suspend fun downloadFile(link: String, requireContext: Context) {
        withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            val createdAt = Date()
            val timestamp = createdAt.time
            val myNewFolder = requireContext.getExternalFilesDir("myNewFolder")
            val myNewFileName = "${timestamp}_${getFileName(link)}"
            val myNewFile = File(myNewFolder, myNewFileName)
            val sharedPreferences =
                requireContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            if (sharedPreferences.contains(link)) {
                fileExists = true
            } else {
                try {
                    myNewFile.outputStream().use { fileOutputStream ->
                        Networking.api
                            .getFile(link)
                            .byteStream()
                            .use { inputStream ->
                                inputStream.copyTo(fileOutputStream)
                            }
                    }
                    sharedPreferences.edit()
                        .putString(link, myNewFileName)
                        .apply()
                } catch (t: Throwable) {
                    myNewFile.delete()
                }
            }
        }
    }

    fun getFileName(url: String): String {
        require(url.isNotEmpty()) { "Url can not be empty" }
        val fileName: String?
        val i = url.lastIndexOf('/')
        return if (i > -1) {
            fileName = url.substring(url.lastIndexOf('/') + 1)
            fileName
        } else {
            throw IllegalArgumentException("Url is not valid, url: $url") //or change on more smart
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "skillbox_shared_pref"
    }
}