package com.example.hw_files.data

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class FilesRepository {

    var fileExistsOrDownloaded = ""
    private lateinit var myNewFile: File

    suspend fun downloadAssetsFiles(requireContext: Context, resources: Resources) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                Log.d("startApp: ", "first time")
                try {
                    val assetsLinks = resources.assets.open("AutoDownloadLink.txt")
                        .bufferedReader()
                        .use {
                            it.readLines()
                        }

                    assetsLinks.map { link -> downloadFile(link, requireContext) }
                } catch (t: Throwable) {

                }
            }
        }
    }

    //https://gitlab.skillbox.ru/learning_materials/android_basic/-/raw/master/README.md
    suspend fun downloadFile(link: String, requireContext: Context) {
        val sharedPreferences: SharedPreferences =
            requireContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            val createdAt = Date()
            val timestamp = createdAt.time
            val myNewFolder = requireContext.getExternalFilesDir("myNewFolder")
            val myNewFileName = "${timestamp}_${getFileName(link)}"
            if (sharedPreferences.contains(link)) {
                fileExistsOrDownloaded = "this file already exists"
            } else {
                try {
                    myNewFile = File(myNewFolder, myNewFileName)
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
                    fileExistsOrDownloaded = "file ${getFileName(link)} downloaded"

                } catch (t: Throwable) {
                    myNewFile.delete()
                }
            }
        }
    }

    private fun getFileName(url: String): String {
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