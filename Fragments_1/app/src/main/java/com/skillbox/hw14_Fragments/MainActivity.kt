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

        replaceLoginFragment()
    }

    private fun replaceLoginFragment() {
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null
//благодаря проверке, после переворота, фрагмент не переписывается занова
        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }
    }

}

