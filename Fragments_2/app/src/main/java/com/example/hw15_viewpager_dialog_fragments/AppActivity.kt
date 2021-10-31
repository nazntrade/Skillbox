package com.example.hw15_viewpager_dialog_fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw15_viewpager_dialog_fragments.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addViewPagerFragment()
    }

    private fun addViewPagerFragment() {
        val viewPagerFragment = ViewPagerFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerAppActivity, viewPagerFragment)
            .commit()
    }
}