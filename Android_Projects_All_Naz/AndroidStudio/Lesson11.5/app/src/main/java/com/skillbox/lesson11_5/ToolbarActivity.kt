package com.skillbox.lesson11_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ToolbarActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        supportActionBar?.title = "New title from code"

    }
}