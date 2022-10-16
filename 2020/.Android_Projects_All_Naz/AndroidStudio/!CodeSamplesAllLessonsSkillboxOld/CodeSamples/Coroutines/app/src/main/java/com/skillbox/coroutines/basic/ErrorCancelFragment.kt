package com.skillbox.coroutines.basic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.skillbox.coroutines.R
import kotlinx.android.synthetic.main.fragment_error_cancel.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class ErrorCancelFragment : Fragment(R.layout.fragment_error_cancel) {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("ErrorCancelFragment", "error from CoroutineExceptionHandler", throwable)
        launchCoroutineAfterError()
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nonCancellableExample()
    }

    private fun errorHandleExample() {
        scope.launch {
            error("test exception")
        }
    }

    private fun nonCancellableExample() {
        scope.launch {
            var i = 0
            withContext(Dispatchers.Default) {
                while (true) {
                    Thread.sleep(500)
                    Log.d("ErrorCancelFragment", "log $i")
                    i++
                }
            }
        }
    }

    private fun cancellableExample() {
        scope.launch {
            withContext(Dispatchers.Default) {
                suspendCancellableCoroutine<Unit> { continuation ->
                    continuation.invokeOnCancellation {
                        Log.d("ErrorCancelFragment", "coroutine cancelled")
                    }
                    var i = 0
                    while (true) {
                        Thread.sleep(500)
                        Log.d("ErrorCancelFragment", "log $i")
                        i++
                    }
                }
            }
        }
    }

    private fun cancellableWithYieldExample() {
        scope.launch {
            withContext(Dispatchers.Default) {
                var i = 0
                while (true) {
                    yield()
                    Thread.sleep(500)
                    Log.d("ErrorCancelFragment", "log $i")
                    i++
                }
            }
        }
    }

    private fun launchCoroutineAfterError() {
        scope.launch {
            Log.d("ErrorCancelFragment", "coroutine launched after error")
        }
    }

    private fun cancelScopeChildrenCoroutines() {
        scope.launch {
            delay(3000)
            error("test exception")
        }

        scope.launch {
            var i = 0
            while (true) {
                delay(500)
                Log.d("ErrorCancelFragment", "log $i")
                i++
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cancelButton.setOnClickListener {
            scope.coroutineContext.cancelChildren()
        }
    }
}