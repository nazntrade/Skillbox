package com.skillbox.hw_multithreading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw_multithreading.databinding.FragmentRaceConditionBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@InternalCoroutinesApi
class RaceConditionFragment : Fragment(R.layout.fragment_race_condition) {

    private lateinit var binding: FragmentRaceConditionBinding
    private var commonRes = 0
    private var threadCount = 0
    private var increment = 0
    private var expectedResult = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRaceConditionBinding.bind(view)

        actionsAfterPressingButtons()
    }

    private fun actionsAfterPressingButtons() {
        listOf(binding.notSynchronizeButton, binding.synchronizeButton).forEach { it ->
            it.setOnClickListener {
                if (binding.threadCountEditText.text.isNotEmpty() &&
                    binding.incrementEditText.text.isNotEmpty()
                ) {
                    clearValue()
                    setupValueFromEditText()
                    calculateExpectedResult()
                    when (binding.notSynchronizeButton) {
                        it ->
                            calculationWithDataRace()
                    }
                    when (binding.synchronizeButton) {
                        it ->
                            calculationWithSynchronize()
                    }
                    outPutText()
                }
            }
        }
    }

    private fun setupValueFromEditText() {
        increment = binding.incrementEditText.text.toString().toInt()
        threadCount = binding.threadCountEditText.text.toString().toInt()
    }

    private fun outPutText() {
        "Calculated: $commonRes\nExpected: $expectedResult".also {
            binding.outputValueTextView.text = it
        }
    }

    private fun clearValue() {
        binding.outputValueTextView.text = ""
        commonRes = 0
        threadCount = 0
        increment = 0
    }

    private fun calculateExpectedResult(): Int {
        expectedResult = commonRes + threadCount * increment
        return expectedResult
    }

    private fun calculationWithDataRace(): Int {
        val timeBefore = System.currentTimeMillis()
        (0 until threadCount).map {
            Thread {
                for (i in 0 until increment) {
                    commonRes++
                }
            }.apply { start() }
        }.map { it.join() }
        "Time not sync: ${calculateTime(timeBefore)}".also { binding.timeNotSynTextView.text = it }
        return commonRes
    }

    private fun calculationWithSynchronize(): Int {
        val timeBefore = System.currentTimeMillis()
        (0 until threadCount).map {
            Thread {
                synchronized(this) {
                    for (i in 0 until increment) {
                        commonRes++
                    }
                }
            }.apply { start() }
        }.map { it.join() }
        "Time sync: ${calculateTime(timeBefore)}".also { binding.timeSynTextView.text = it }
        return commonRes
    }

    private fun calculateTime(timeBefore: Long): Long {
        return System.currentTimeMillis() - timeBefore
    }
    //RaceCondition hardly reproduced at values less than 100
}