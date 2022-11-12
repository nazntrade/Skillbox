package com.skillbox.lesson11_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.lesson11_5.databinding.ActivityCoordinatorBinding

class CoordinatorActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoordinatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoordinatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}