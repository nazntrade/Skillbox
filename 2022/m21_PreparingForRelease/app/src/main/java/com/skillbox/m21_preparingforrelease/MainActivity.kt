package com.skillbox.m21_preparingforrelease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skillbox.m21_preparingforrelease.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (BuildConfig.FREE) {}

        binding.message.text = ResourceValueImpl().getValue()
    }
}