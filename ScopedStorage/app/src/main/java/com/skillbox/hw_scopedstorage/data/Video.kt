package com.skillbox.hw_scopedstorage.data

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.time.Duration

data class Video(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Int, ///????
    val size: Int,
)
