package com.skillbox.hw_m2_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skillbox.hw_m2_layout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customView.setImage1(R.drawable.image_sample)
        binding.customView.setImage2(R.drawable.joke)
        binding.customView.setText1("Andrew Backer")
        binding.customView.setText2("A programmer is a person who fixed a problem that you don’t know you have, in a way you don’t understand.")
    }
}
