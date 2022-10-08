//package lt.vitalikas.scopedstorage.data.repositories
//
//import android.annotation.SuppressLint
//import android.content.ContentUris
//import android.content.ContentValues
//import android.content.Context
//import android.database.ContentObserver
//import android.net.Uri
//import android.os.Handler
//import android.os.Looper
//import android.provider.DocumentsContract
//import android.provider.MediaStore
//import kotlinx.coroutines.*
//import lt.vitalikas.scopedstorage.data.apis.FileApiImpl
//import lt.vitalikas.scopedstorage.domain.models.Video
//import lt.vitalikas.scopedstorage.domain.repositories.VideoRepo
//import lt.vitalikas.scopedstorage.utils.hasQ
//
//class VideoRepoImpl(private val context: Context) : VideoRepo {
//
//    private lateinit var observer: ContentObserver
//
//    private lateinit var videoUri: Uri
//
//    override suspend fun readVideos(): List<Video> {
//
//        val videos = mutableListOf<Video>()
//
//        withContext(Dispatchers.IO) {
//            context.contentResolver.query(
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//            )?.use { cursor ->
//                while (cursor.moveToNext()) {
//                    val id =
//                        cursor.getLong(cursor
//                            .getColumnIndex(MediaStore.Video.VideoColumns._ID)
//                            .takeIf { it >= 0 }
//                            ?: error("Error getting data from cursor")
//                        )
//
//                    val name =
//                        cursor.getString(cursor
//                            .getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
//                            .takeIf { it >= 0 }
//                            ?: error("Error getting data from cursor")
//                        )
//
//                    val size =
//                        cursor.getInt(cursor
//                            .getColumnIndex(MediaStore.Video.VideoColumns.SIZE)
//                            .takeIf { it >= 0 }
//                            ?: error("Error getting data from cursor")
//                        )
//
//                    val uri = ContentUris.withAppendedId(
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                        id
//                    )
//
//                    videos += Video(id, uri, name, size)
//                }
//            }
//        }
//
//        return videos
//    }
//
//    override suspend fun saveVideo(uri: Uri, name: String, url: String): Boolean {
//        val job = Job()
//        CoroutineScope(job + Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
//            DocumentsContract.deleteDocument(context.contentResolver, uri)
////            context.contentResolver.delete(uri, null, null)
//        }).launch {
////            val videoUri = createVideoUri(name)
//            fetchVideo(url, uri)
////            if (hasQ()) {
////                makeVideoVisible(uri)
////            }
//        }.join()
//        return job.complete()
//    }
//
//    override suspend fun deleteVideo(id: Long) {
//        withContext(Dispatchers.IO) {
//            val uri = ContentUris.withAppendedId(
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                id
//            )
//            context.contentResolver.delete(uri, null, null)
//        }
//    }
//
//    private fun createVideoUri(name: String): Uri {
//        val volume = if (hasQ()) {
//            MediaStore.VOLUME_EXTERNAL_PRIMARY
//        } else {
//            MediaStore.VOLUME_EXTERNAL
//        }
//
//        val videoContentUri = MediaStore.Video.Media.getContentUri(volume)
//
//        val videoContentValues = ContentValues().apply {
//            put(MediaStore.Video.Media.DISPLAY_NAME, name)
//            put(MediaStore.Video.Media.MIME_TYPE, VIDEO_MP4)
//            if (hasQ()) {
//                put(MediaStore.Video.Media.IS_PENDING, PENDING_TRUE_CODE)
//            }
//        }
//
//        videoUri = context.contentResolver.insert(videoContentUri, videoContentValues)
//            ?: error("Error creating video uri")
//
//        return videoUri
//    }
//
//    private suspend fun fetchVideo(url: String, uri: Uri) {
//        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
//            FileApiImpl.fileApi
//                .downloadFile(url)
//                .byteStream()
//                .use { inputStream ->
//                    inputStream.copyTo(outputStream)
//                }
//        }
//    }
//
//    @SuppressLint("InlinedApi")
//    private fun makeVideoVisible(videoUri: Uri) {
//        val videoContentValues = ContentValues().apply {
//            put(MediaStore.Video.Media.IS_PENDING, PENDING_FALSE_CODE)
//        }
//        context.contentResolver.update(videoUri, videoContentValues, null, null)
//    }
//
//    fun observeVideos(
//        onChange: () -> Unit
//    ) {
//        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
//            override fun onChange(selfChange: Boolean) {
//                super.onChange(selfChange)
//                onChange()
//            }
//        }
//        context.contentResolver.registerContentObserver(
//            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//            true,
//            observer
//        )
//    }
//
//    fun unregisterObserver() {
//        context.contentResolver.unregisterContentObserver(observer)
//    }
//
//    companion object {
//        const val VIDEO_MP4 = "video/mp4"
//        const val PENDING_TRUE_CODE = 1
//        const val PENDING_FALSE_CODE = 0
//    }
//
//}