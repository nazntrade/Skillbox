package com.skillbox.lesson11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.skillbox.lesson11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleLastNameButton.setOnClickListener {
            binding.userInfoGroup.isVisible = !binding.userInfoGroup.isVisible
        }

        binding.userInfoGroup.referencedIds.forEach { id ->
            findViewById<View>(id).setOnClickListener {
                Toast.makeText(this, "clicked On View $id In Group", Toast.LENGTH_SHORT).show()
            }
        }
    }
}