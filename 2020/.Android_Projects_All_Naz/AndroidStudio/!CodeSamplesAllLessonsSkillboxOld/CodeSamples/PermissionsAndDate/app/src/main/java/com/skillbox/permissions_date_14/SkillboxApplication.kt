package com.skillbox.permissions_date_14

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class SkillboxApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}