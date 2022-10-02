package com.skillbox.hw_scopedstorage.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.skillbox.hw_scopedstorage.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.use
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream


class VideosRepository(
    private val context: Context
) {
    private var observer: ContentObserver? = null

    private lateinit var newFile: File

    private val directoryDownloads =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    val DOWNLOAD_DIR = File(directoryDownloads, "ScopedStorageProject")

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
    suspend fun saveVideo(name: String, url: String) {
        withContext(Dispatchers.IO) {
            val videoUri = createVideoUri(name, url)
            /*val downloadedFile =*/ downloadVideo(url, videoUri)
            makeVideoVisible(videoUri)
//            if (haveQ().not()) {
//
//                // File(videoUri.path.toString())
//                val finalUri: Uri? = copyFileToDownloads(downloadedFile)
//            }
        }
    }

    suspend fun saveVideoToCustomDir(url: String, uri: Uri) {
        withContext(Dispatchers.IO) {
            downloadVideo(url, uri)
        }
    }

    private fun createVideoUri(name: String, url: String): Uri {

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
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(videoCollection, videoDetails)!!
    }

    private suspend fun downloadVideo(url: String, uri: Uri)/*: File */{
        withContext(Dispatchers.IO) {
            try {
                if (haveQ().not()) {
                    if (DOWNLOAD_DIR.exists().not()) {
                        DOWNLOAD_DIR.mkdir()
                    }
                    newFile = File(DOWNLOAD_DIR, getFileName(url))
                    newFile.outputStream().use { outputStream ->
                        Networking.api
                            .getFile(url)
                            .byteStream()
                            .use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                    }
                } else {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        Networking.api
                            .getFile(url)
                            .byteStream()
                            .use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                    }
                }
            } catch (t: Throwable) {
                Timber.e(t)
                context.contentResolver.delete(uri, null, null)
            }
        }
//         return newFile
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

    private suspend fun copyFileToDownloads(downloadedFile: File): Uri? {
        val resolver = context.contentResolver
        return withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
//                put(MediaStore.MediaColumns.DISPLAY_NAME, downloadedFile.name)
//                put(MediaStore.MediaColumns.MIME_TYPE, getMimeType(downloadedFile))
//                put(MediaStore.MediaColumns.SIZE, getFileSize(downloadedFile))
                }
                resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            } else {
                val authority = "${context.packageName}.provider"
                val destinyFile = File(DOWNLOAD_DIR, downloadedFile.name)
                FileProvider.getUriForFile(context, authority, destinyFile)
            }?.also { downloadedUri ->
                resolver.openOutputStream(downloadedUri).use { outputStream ->
                    val brr = ByteArray(1024)
                    var len: Int
                    val bufferedInputStream =
                        BufferedInputStream(FileInputStream(downloadedFile.absoluteFile))
                    while ((bufferedInputStream.read(brr, 0, brr.size).also { len = it }) != -1) {
                        outputStream?.write(brr, 0, len)
                    }
                    outputStream?.flush()
                    bufferedInputStream.close()
                }
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


//    companion object {
//        const val SHARED_PREFS_NAME = "shared_pref"
//    }
}