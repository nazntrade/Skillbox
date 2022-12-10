package com.skillbox.m11_timer_data_storage.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class Repository(
    private val context: Context
) {

    private lateinit var sharedPreferences: SharedPreferences
    private var localVariable: String? = null
    private var messageText: String? = null

    private suspend fun getDataFromSharedPreference(): String? {
        withContext(Dispatchers.IO) {
            try {
                messageText = sharedPreferences.getString(SHARED_PREFS_KEY, "")
            } catch (t: Throwable) {
                Timber.tag("getDataFromSharedPreference").d("$t")
            }
        }
        return messageText
    }


    private fun getDataFromLocalVariable(): String? {
        return localVariable
    }

    suspend fun saveText(text: String) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
                try {
                    sharedPreferences.edit()
                        .putString(SHARED_PREFS_KEY, text)
                        .apply()
                    Timber.tag("saveText").d("to_sharedPrefs")

                    localVariable = text
                    Timber.tag("saveText").d("to_localVariable")

                } catch (t: Throwable) {
                    Timber.tag("saveText").e(t)
                }
            }
        }
    }

    suspend fun clearText(): String? {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
                try {
                    sharedPreferences
                        .edit()
                        .remove(SHARED_PREFS_KEY)
                        .apply()
                    Timber.tag("clearText").d("sharedPrefs")

                    localVariable = null
                    Timber.tag("clearText").d("localVariable")
                } catch (t: Throwable) {
                    Timber.tag("clearText").e(t)
                }
            }
        }
        return getText()
    }

    suspend fun getText(): String? {
        withContext(Dispatchers.IO) {
            try {
                sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
                messageText = when {
                    localVariable != null -> getDataFromLocalVariable()
                    sharedPreferences.contains(SHARED_PREFS_KEY) -> getDataFromSharedPreference()
                    else -> {
                        ""
                    }
                }
            } catch (t: Throwable) {
                Timber.tag("getText").d("$t")
            }
        }
        return messageText
    }

    companion object {
        private const val SHARED_PREFS_NAME = "hw11_shared_pref"
        private const val SHARED_PREFS_KEY = "key"
    }
}