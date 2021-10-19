package com.skillbox.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val logTag = "MyActivity"

    private var state: CounterStateParcelable = CounterStateParcelable(count = 0, someString = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "string onCreate ${getString(R.string.increase)}")
        Log.d(logTag, "onCreate ${hashCode()}")

        if(savedInstanceState != null) {
            state = savedInstanceState.getParcelable(KEY_COUNTER) ?: error("unreachable")
            val serializable: CounterState = savedInstanceState.getSerializable("a") as CounterState
            Log.d(logTag, serializable.toString())
        }

        initLayout()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_COUNTER, state)
        outState.putSerializable("a", CounterState(1, "32423"))

    }

    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart ${hashCode()}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(logTag, "onRestart ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(logTag, "onResume ${hashCode()}")
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Log.d(logTag, "onTopResumedActivityChanged $isTopResumedActivity ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(logTag, "onPause ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(logTag, "onStop ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(logTag, "onDestroy ${hashCode()}")
    }

    private fun initLayout() {
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.increaseCounter).setOnClickListener {
//            counter++
            state = state.getIncrementedState()
            updateCounterText()
        }

        findViewById<Button>(R.id.decreaseCounter).setOnClickListener {
//            counter--
            state = state.getDecrementedState()
            updateCounterText()
        }

        updateCounterText()
    }

    private fun updateCounterText() {
        findViewById<TextView>(R.id.counter).setText(state.toString())
    }

    companion object {
        private const val KEY_COUNTER = "counter"
    }
}
