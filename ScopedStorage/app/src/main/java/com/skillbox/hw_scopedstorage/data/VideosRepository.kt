package com.skillbox.hw_scopedstorage.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.skillbox.hw_scopedstorage.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

class VideosRepository(
    private val context: Context
) {

    private var observer: ContentObserver? = null

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var newFile: File

    private val name1 = "simpleVideo1"
    private val url1 =
        "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    private val name2 = "simpleVideo2"
    private val url2 = "https://download.samplelib.com/mp4/sample-5s.mp4"
    private val name3 = "simpleVideo3"
    private val url3 =
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/720/Big_Buck_Bunny_720_10s_1MB.mp4"
    private val name4 = "simpleVideo4"
    private val url4 =
        "https://freetestdata.com/wp-content/uploads/2022/02/Free_Test_Data_1MB_MP4.mp4"

    suspend fun initExistedVideo(requireContext: Context) {
        withContext(Dispatchers.IO) {
            sharedPreferences =
                requireContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            try {
                val sharedPrefExistedValue =
                    sharedPreferences.getBoolean("first_run", true)
                if (sharedPrefExistedValue) {
                    Timber.tag("first_run: ").d("true")

                    withContext(Dispatchers.IO) {

                        saveVideo(name3, url3)
                        saveVideo(name4, url4)
                        saveVideo(name2, url2)
                        saveVideo(name1, url1)
                    }

                    sharedPreferences.edit()
                        .putBoolean("first_run", false)
                        .apply()
                }
            } catch (t: Throwable) {

            }
        }
    }

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
        withContext(Dispatchers.IO) {
            val videoUri = saveVideoDetails(name)
            downloadVideo(url, videoUri)
            makeVideoVisible(videoUri)
        }
    }

    //!!!!!!!!!!!!
    private fun saveVideoDetails(name: String): Uri {
        val volume =
            if (haveQ()) {
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            } else {
                MediaStore.VOLUME_EXTERNAL
            }

        val videoCollectionUri = MediaStore.Video.Media.getContentUri(volume)
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

    private fun makeVideoVisible(imageUri: Uri) {
        if (haveQ().not()) return

        val imageDetails = ContentValues().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 0)
            }
        }
        context.contentResolver.update(imageUri, imageDetails, null, null)
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

    companion object {
        const val SHARED_PREFS_NAME = "shared_pref"
    }

}