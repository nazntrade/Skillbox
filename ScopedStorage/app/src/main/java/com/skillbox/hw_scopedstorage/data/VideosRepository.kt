package com.skillbox.hw_scopedstorage.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.skillbox.hw_scopedstorage.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(
    private val context: Context
) {

    private var observer: ContentObserver? = null

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
                    val name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                    val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                    val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

                    videoList += Video(id, uri, name, duration, size)
                }
            }
        }
        return videoList
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun saveVideo(name: String, url: String) {
        withContext(Dispatchers.IO) {
            val videoUri = saveVideoDetails(name)
            downloadVideo(url, videoUri)
            makeVideoVisible(videoUri)
        }
    }

        //!!!!!!!!!!!!
    @RequiresApi(Build.VERSION_CODES.Q)// this annotation ???
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
            put(MediaStore.Video.Media.MIME_TYPE, "video/*")
            if (haveQ()){
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

//    private fun makeVideoVisible(imageUri: Uri) {
//        if(haveQ().not()) return
//
//        val imageDetails = ContentValues().apply {
//            put(MediaStore.Images.Media.IS_PENDING, 0)
//        }
//        context.contentResolver.update(imageUri, imageDetails, null, null)
//    }
//
//    private suspend fun downloadVideo(url: String, uri: Uri) {
//        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
//            Networking.api
//                .getFile(url)
//                .byteStream()
//                .use { inputStream ->
//                    inputStream.copyTo(outputStream)
//                }
//        }
//    }


    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            context.contentResolver.delete(uri, null, null)
        }
    }

    fun unregisterObserver() {
        observer?.let { context.contentResolver.unregisterContentObserver(it) }
    }

}