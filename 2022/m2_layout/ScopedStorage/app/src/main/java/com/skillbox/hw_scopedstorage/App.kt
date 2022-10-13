package com.skillbox.hw_scopedstorage

import android.app.Application
import android.widget.Toast
import com.skillbox.hw_scopedstorage.BuildConfig
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}