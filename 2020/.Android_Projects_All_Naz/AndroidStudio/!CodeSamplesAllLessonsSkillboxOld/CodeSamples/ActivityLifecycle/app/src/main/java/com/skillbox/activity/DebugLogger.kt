package com.skillbox.activity

import android.util.Log

object DebugLogger {
    fun d(tag: String, message: String) {
        if(BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
}