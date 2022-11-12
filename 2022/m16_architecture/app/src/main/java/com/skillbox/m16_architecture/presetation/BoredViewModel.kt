package com.skillbox.m16_architecture.presetation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.skillbox.m16_architecture.data.BoredRepository

class BoredViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = BoredRepository(app)

}