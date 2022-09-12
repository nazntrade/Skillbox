package com.skillbox.hw_scopedstorage.data

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import kotlinx.parcelize.Parcelize
import java.time.Duration

@Parcelize
data class Video(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Int, ///????
    val size: Int,
) : Parcelable
