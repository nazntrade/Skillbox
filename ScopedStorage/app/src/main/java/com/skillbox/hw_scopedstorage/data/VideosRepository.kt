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

    // https://rr1---sn-5hne6nz6.googlevideo.com/videoplayback?expire=1662118300&ei=PJURY6HIGJaAgAevtY3oAw&ip=216.131.88.183&id=o-ALADGcK5KGngW7zCeMd0ZMdSw21inZo2xbKLUqUWjirx&itag=18&source=youtube&requiressl=yes&mh=X2&mm=31%2C29&mn=sn-5hne6nz6%2Csn-5hnekn7k&ms=au%2Crdu&mv=m&mvi=1&pl=23&initcwndbps=2340000&spc=lT-KhnhyA580qaPO-ygHCLZfqdKsDIo&vprv=1&mime=video%2Fmp4&ns=ulpaoy3ciJCcNGKzIQ7DxdwH&cnr=14&ratebypass=yes&dur=65.062&lmt=1627138294836796&mt=1662096327&fvip=4&fexp=24001373%2C24007246&c=WEB&rbqsm=fr&n=Pla8AkqhysmJAQ&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgQs_et9PdttW2LAlI9cX61MB3wiEjZo74Cfj18mfwxScCIQC5O_IaWAF0PINmYRmnAstn_UiZfPKzq0qVmcsYHvBeqw%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAKvYFwm3xYV_QqwAX2805o_JNlDYmhN9KjADYARJFLEqAiBwtcoljRD4kSFxOaE0ADVipk96wOdt8_ntEVV-BvHmpQ%3D%3D

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

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun makeVideoVisible(imageUri: Uri) {
        if(haveQ().not()) return

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(imageUri, imageDetails, null, null)
    }

    private suspend fun downloadVideo(url: String, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            Networking.api
                .getFile(url)
                .byteStream()
                .use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
        }
    }


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