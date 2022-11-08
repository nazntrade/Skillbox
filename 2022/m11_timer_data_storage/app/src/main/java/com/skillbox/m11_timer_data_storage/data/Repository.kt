package com.skillbox.m11_timer_data_storage.data

import android.content.SharedPreferences

class Repository {

    private lateinit var sharedPreferences: SharedPreferences

    private fun getDataFromSharedPreference(): String? {

        return String()
    }

    private fun getDataFromLocalVariable(): String? {

        return String()
    }

    fun saveText(text: String) {

    }

    fun clearText() {

    }

    fun getText(): String {

        getDataFromSharedPreference()

        return String()
    }

    companion object {
        private const val SHARED_PREFS_NAME = "hw11_shared_pref"
    }

}