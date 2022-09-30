package com.skillbox.hw_scopedstorage.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.skillbox.hw_scopedstorage.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.util.*
import kotlin.coroutines.coroutineContext

class VideosRepository(
    private val context: Context
) {

    private var observer: ContentObserver? = null
    private lateinit var myNewFile: File

    val listSampleVideos = listOf(
        SampleVideo(
            "sampleVideo1",
            "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        ),
        SampleVideo(
            "sampleVideo2",
            "https://download.samplelib.com/mp4/sample-5s.mp4"
        ),
        SampleVideo(
            "sampleVideo3",
            "https://freetestdata.com/wp-content/uploads/2022/02/Free_Test_Data_1MB_MP4.mp4"
        ))

    @SuppressLint("Range")
    suspend fun getVideo(): List<Video> {
        val videoList = mutableListOf<Video>()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                    val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val duration =
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

                    videoList += Video(id, uri, name, duration, size)
                }
            }
        }
        return videoList
    }

    suspend fun saveVideo(name: String, url: String) {
        if (haveQ()) {
            withContext(Dispatchers.IO) {
                val videoUri = saveVideoDetails(name)
                downloadVideo(url, videoUri)
                makeVideoVisible(videoUri)
            }
        } else {
            saveVideoLowerAndroidQ(name, url)
        }
    }

    private suspend fun saveVideoLowerAndroidQ(name: String, url: String) {
        withContext(Dispatchers.IO) {
//            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
//            val myNewFolder = requireContext.getExternalFilesDir("myNewFolder")
            val absolutePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

            val path = absolutePath.substring(0, absolutePath.length - 5) + "/Coutloot/"
            val filePath = File(path)
            myNewFile = File(path, name)
            if (!myNewFile.exists())
                myNewFile.mkdir()
            try {
                myNewFile.outputStream().use { fileOutputStream ->
                    Networking.api
                        .getFile(url)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(fileOutputStream)
                        }
                }
            } catch (t: Throwable) {
                myNewFile.delete()
            }

        }
    }

    suspend fun saveVideoToCustomDir(url: String, uri: Uri) {
        withContext(Dispatchers.IO) {
            downloadVideo(url, uri)
        }
    }

    private fun saveVideoDetails(name: String): Uri {

        val videoCollectionUri =
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

    private fun makeVideoVisible(videoUri: Uri) {
        if (haveQ().not()) return

        val videoDetails = ContentValues().apply {
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 0)
            }
        }
        context.contentResolver.update(videoUri, videoDetails, null, null)
    }

    private suspend fun downloadVideo(url: String, uri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    Networking.api
                        .getFile(url)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                }
            } catch (t: Throwable) {
                Timber.e(t)
                context.contentResolver.delete(uri, null, null)
            }
        }
    }

    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            context.contentResolver.delete(uri, null, null)
        }
    }

    // observe all change in videoFiles
    fun observeVideo(onChange: () -> Unit) {
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            observer!!
        )
    }

    fun unregisterObserver() {
        observer?.let { context.contentResolver.unregisterContentObserver(it) }
    }

//    companion object {
//        const val SHARED_PREFS_NAME = "shared_pref"
//    }
}