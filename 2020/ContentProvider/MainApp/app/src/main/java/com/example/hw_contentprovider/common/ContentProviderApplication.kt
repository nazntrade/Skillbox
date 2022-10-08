package com.example.hw_contentprovider.common

import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.example.hw_contentprovider.BuildConfig

class ContentProviderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Log.d("onCreateApplication", "Start ContentProviderApplication.")
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDeath()
                    .build()
            )
        }
    }
}
