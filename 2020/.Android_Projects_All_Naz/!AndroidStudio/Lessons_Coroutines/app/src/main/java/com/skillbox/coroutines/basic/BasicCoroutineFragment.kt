package com.skillbox.coroutines.basic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.math.BigInteger
import kotlin.random.Random
import kotlin.random.asJavaRandom

class BasicCoroutineFragment : Fragment() {

    private val fragmentScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asyncExample()
    }

    private fun asyncExample() {
        val fragmentIOScope = CoroutineScope(Dispatchers.IO)

        fragmentIOScope.launch {

            (0 until 100).map {
                async {
                    calculateNumber()
                }
            }
                .map {
                    it.await()
                }
        }
    }

    private fun calculateNumberExample() {
        fragmentScope.launch {
            Log.d("BasicCoroutineFragment", "launch inside from thread = ${Thread.currentThread().name}")
            calculateNumber()
            Log.d("BasicCoroutineFragment", "launch inside from thread = ${Thread.currentThread().name}")
        }
        Log.d("BasicCoroutineFragment", "Coroutine launched")
    }

    private fun changeThreadExample() {
        val fragmentIOScope = CoroutineScope(Dispatchers.IO)
        fragmentIOScope.launch {
            (0..200).forEach {
                Log.d("BasicCoroutineFragment", "start from thread = ${Thread.currentThread().name}")
                delay(100)
                Log.d("BasicCoroutineFragment", "end from thread = ${Thread.currentThread().name}")
            }
        }
    }

    private suspend fun calculateNumber(): BigInteger {
        return withContext(Dispatchers.Default) {
            Log.d(
                "BasicCoroutineFragment",
                "calculate number thread = ${Thread.currentThread().name}"
            )
            BigInteger.probablePrime(2000, Random.asJavaRandom())
        }
    }
}