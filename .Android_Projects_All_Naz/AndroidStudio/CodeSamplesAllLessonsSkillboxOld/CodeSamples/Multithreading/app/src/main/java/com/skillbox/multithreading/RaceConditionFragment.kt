package com.skillbox.multithreading

import android.widget.Toast
import androidx.fragment.app.Fragment

class RaceConditionFragment: Fragment() {

    private var value: Int = 0

    override fun onResume() {
        super.onResume()
        makeMultithreadingIIncrement()
    }

    private fun makeMultithreadingIIncrement() {
        val threadCount = 100
        val incrementCount = 1000000
        val expectedValue = value + threadCount * incrementCount

        (0 until threadCount).map {
            Thread {
                synchronized(this) {
                    for (i in 0 until incrementCount) {
                        value++
                    }
                }
            }.apply {
                start()
            }
        }
            .map { it.join() }
        Toast.makeText(requireContext(), "value=$value, expected=$expectedValue", Toast.LENGTH_LONG).show()
    }

}