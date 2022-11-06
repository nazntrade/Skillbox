package com.skillbox.m11_timer_data_storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skillbox.m11_timer_data_storage.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}