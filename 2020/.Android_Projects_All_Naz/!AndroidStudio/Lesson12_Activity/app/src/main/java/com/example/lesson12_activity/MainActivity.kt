package com.example.lesson12_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lesson12_activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    private var state: CounterState = CounterState(0, "")

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DebugLogger.d(tag, "onCrate ${hashCode()}")

        if (savedInstanceState != null) {
            state = savedInstanceState.getParcelable(KEY_COUNTER) ?: error("Unexpected state")
        }

        binding.buttonIncrease.setOnClickListener {
            state = state.increment()
            updateCurrentText()
        }
        binding.buttonDecrease.setOnClickListener {
            state = state.decrement()
            updateCurrentText()
        }
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(tag, "onStart ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.d(tag, "onResume ${hashCode()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_COUNTER, state)
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        DebugLogger.d(tag, "onTopResumedActivityChanged ${hashCode()} $isTopResumedActivity")
    }

    override fun onPause() {
        super.onPause()
        DebugLogger.d(tag, "onPause ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        DebugLogger.d(tag, "onStop ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.d(tag, "onDestroy ${hashCode()}")
    }

    private fun updateCurrentText() {
        binding.counterTextView.text = state.toString()
    }

    companion object {
        private const val KEY_COUNTER = "counter"
    }
}

object DebugLogger {
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
}