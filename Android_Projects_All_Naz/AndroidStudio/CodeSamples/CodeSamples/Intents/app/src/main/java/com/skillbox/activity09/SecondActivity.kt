package com.skillbox.activity09

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity: AppCompatActivity(R.layout.activity_second) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LifecycleTest", "SecondActivity|onCreate|${hashCode()}")
        val message = intent.getStringExtra(KEY_MESSAGE)
        messageTextView.text = message
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifecycleTest", "SecondActivity|onStart|${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifecycleTest", "SecondActivity|onResume|${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifecycleTest", "SecondActivity|onPause|${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifecycleTest", "SecondActivity|onStop|${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleTest", "SecondActivity|onDestroy|${hashCode()}")
    }

    companion object {
        private const val KEY_MESSAGE = "message key"

        fun getIntent(context: Context, message: String?): Intent {
            return Intent(context, SecondActivity::class.java)
                .putExtra(KEY_MESSAGE, message)
        }
    }

}