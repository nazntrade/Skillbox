package com.example.lessons_16_lists1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons_16_lists1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PersonListFragment())
                .commit()
        }
    }
}
