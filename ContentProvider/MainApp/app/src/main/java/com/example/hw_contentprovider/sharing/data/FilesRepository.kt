package com.example.hw_contentprovider.sharing.data

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.hw_contentprovider.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class FilesRepository(private val context: Context) {

    var fileExistsOrDownloaded = ""
    private lateinit var myNewFile: File
    private lateinit var sharedPreferences: SharedPreferences
    var shareIntent: Intent? = null

    suspend fun downloadAssetsFiles(requireContext: Context, resources: Resources) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
                sharedPreferences =
                    requireContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                try {
                    val firstRun = sharedPreferences.getBoolean("firstRun", true)
                    if (firstRun) {
                        Log.d("startApp: ", "first time")
                        val assetsLinks = resources.assets.open("AutoDownloadLink.txt")
                            .bufferedReader()
                            .use {
                                it.readLines()
                            }

                        assetsLinks.map { link -> downloadFile(link, requireContext) }
                        sharedPreferences.edit()
                            .putBoolean("firstRun", false)
                            .apply()
                    }
                } catch (t: Throwable) {
                }
            }
        }
    }

    // Link for example
    //https://assets.entrepreneur.com/slideshow/20160628202933-image02.jpeg
    suspend fun downloadFile(link: String, requireContext: Context) {
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

    //Extract fileName from link
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

    suspend fun shareFiles() {
        withContext(Dispatchers.IO) {

            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            if (myNewFile.exists().not()) return@withContext

            val uri = FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.file_provider",
                myNewFile
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, uri)
                type = context.contentResolver.getType(uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            shareIntent = Intent.createChooser(intent, null)
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "skillbox_shared_pref"
    }
}