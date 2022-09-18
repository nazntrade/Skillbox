package com.skillbox.hw_scopedstorage.data

import android.net.Uri
import android.os.Parcelable
import android.util.Size
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration

@Parcelize
data class Video(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Int, ///????
    val size: Int,
) : Parcelable
