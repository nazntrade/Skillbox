package com.skillbox.hw_scopedstorage.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.METADATA_KEY_DURATION
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.skillbox.hw_scopedstorage.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.use
import timber.log.Timber
import java.io.File

class VideosRepository(
    private val context: Context
) {
    private var observer: ContentObserver? = null

    private lateinit var newFile: File

    private val directoryDownloads =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    private val downloadDir = File(directoryDownloads, "ScopedStorageProject")

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
        )
    )

    @SuppressLint("Range")
    suspend fun getVideo(): List<Video> {
        val videoList = mutableListOf<Video>()
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )

        withContext(Dispatchers.IO) {
            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    // Stores column values and the contentUri in a local object
                    // that represents the media file.
                    videoList += Video(id, contentUri, name, duration, size)
                }
            }
        }
        return videoList
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun saveVideo(url: String) {
        withContext(Dispatchers.IO) {
            if (haveQ()) {
                val videoUri = createVideoUri(url)
                downloadVideoQ(url, videoUri)
                makeVideoVisible(videoUri)
            } else {
                if (downloadDir.exists().not()) {
                    downloadDir.mkdir()
                }
                downloadVideo(url, downloadDir.toUri())
                createVideoUri(url)
            }
        }
    }

    suspend fun saveVideoToCustomDir(url: String, uri: Uri) {
        withContext(Dispatchers.IO) {
            downloadVideoQ(url, uri)
        }
    }

    private fun File.getMediaDuration(context: Context): Long {
        if (!exists()) return 0
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.parse(absolutePath))
        val duration = retriever.extractMetadata(METADATA_KEY_DURATION)
        retriever.release()

        return duration?.toLongOrNull()?.div(1000) ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createVideoUri(url: String): Uri {

        val videoCollection =
            if (haveQ()) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, getFileName(url))
            put(MediaStore.Video.Media.TITLE, getFileName(url))
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            } else {
                put(MediaStore.Video.Media.DATA, newFile.absolutePath)
                put(MediaStore.Video.Media.DURATION, newFile.getMediaDuration(context))
            }
        }
        return context.contentResolver.insert(videoCollection, videoDetails)!!
    }

    private suspend fun downloadVideo(url: String, uri: Uri): File {
        withContext(Dispatchers.IO) {
            try {
                newFile = File(downloadDir, getFileName(url))
                newFile.outputStream().use { outputStream ->
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
        return newFile
    }

    private suspend fun downloadVideoQ(url: String, uri: Uri) {
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

    private fun makeVideoVisible(videoUri: Uri) {
        if (haveQ().not()) return

        val videoDetails = ContentValues().apply {
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 0)
            }
        }
        context.contentResolver.update(videoUri, videoDetails, null, null)
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
}