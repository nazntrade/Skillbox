package com.skillbox.homework12_12_Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import androidx.viewbinding.BuildConfig
import com.skillbox.homework12_12_Activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DebugLogger.v(tag, "onCreate ${hashCode()}")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            makeProgressBar()
        }
        binding.inputEmail.setOnClickListener {
            checkButton()
        }
        binding.inputPassword.setOnClickListener {
            checkButton()
        }
        binding.checkbox.setOnClickListener {
            checkButton()
        }
    }

    fun checkButton() {
        binding.button.isEnabled =
            binding.inputEmail.text.isNotEmpty()
                    && binding.inputPassword.text.isNotEmpty()
                    && binding.checkbox.isChecked
    }

    private fun makeProgressBar() {
        val view = layoutInflater.inflate(R.layout.progres_bar, binding.container, false)
        view.apply {
            binding.container.addView(view)
        }
//        binding.progressBar.visibility = View.VISIBLE
        binding.button.isEnabled = false
        binding.inputEmail.isEnabled = false
        binding.inputPassword.isEnabled = false
        binding.checkbox.isEnabled = false

        Handler().postDelayed({
//            binding.progressBar.visibility = View.GONE
            binding.button.isEnabled = true
            binding.inputEmail.isEnabled = true
            binding.inputPassword.isEnabled = true
            binding.checkbox.isEnabled = true
            binding.container.removeView(view)
            Toast.makeText(this, R.string.you_are_logged_in, Toast.LENGTH_LONG).show()
        }, 2000)
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(tag, "onStart ${hashCode()}")
    }
    override fun onResume() {
        super.onResume()
        DebugLogger.i(tag, "onResume ${hashCode()}")
    }
    override fun onPause() {
        super.onPause()
        DebugLogger.w(tag, "onPause ${hashCode()}")
    }
    override fun onStop() {
        super.onStop()
        DebugLogger.e(tag, "onStop ${hashCode()}")
    }
    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.v(tag, "onDestroy ${hashCode()}")
    }
}


object DebugLogger {
    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }
    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }
    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }
}


