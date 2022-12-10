package com.example.core.tools.basefrahment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.tools.all.LoadState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.UnknownHostException

abstract class BaseViewModel :ViewModel(){
    protected val _loadState =
        MutableStateFlow(LoadState.LOADING)
    val loadState = _loadState.asStateFlow()

    protected val handler = CoroutineExceptionHandler { _, t ->
        Log.e("Kart","${t.message} ${t}")
        if (t.message == "HTTP 404 ")
            _loadState.value = LoadState.FHF
        else  if (t != UnknownHostException())
            _loadState.value = LoadState.ERROR
    }
}