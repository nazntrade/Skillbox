package com.example.lessons13_newscreens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons13_newscreens.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra(KEY_MESSAGE)
        binding.messageTextView.text = message

        Log.d("LifecycleTest", "SecondActivity|onCreate|${hashCode()}")
    }

    companion object {
        private const val KEY_MESSAGE = "message key"

        fun getIntent(context: Context, message: String?): Intent {
            return Intent(context, SecondActivity::class.java)
                .putExtra(KEY_MESSAGE, message)
        }
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
}