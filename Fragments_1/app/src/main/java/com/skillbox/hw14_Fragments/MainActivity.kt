package com.skillbox.hw14_Fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.hw14_Fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addLoginFragment()

    }

    private fun addLoginFragment() {
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.containerMainActivity) != null
//благодаря проверке, после переворота, фрагмент не переписывается занова
        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerMainActivity, LoginFragment())
                .commit()
        }
    }

}

