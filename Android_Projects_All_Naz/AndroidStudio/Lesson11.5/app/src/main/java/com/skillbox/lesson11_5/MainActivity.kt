package com.skillbox.lesson11_5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.lesson11_5.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Navigation clicked", Toast.LENGTH_SHORT).show()
        }




    }
}