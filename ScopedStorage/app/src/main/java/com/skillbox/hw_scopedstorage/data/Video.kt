package com.skillbox.hw_scopedstorage.data

import android.net.Uri
import java.time.Duration

data class Video(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Duration, ///????
    val size: Int
)
