package com.skillbox.hw14_Fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        supportFragmentManager.beginTransaction()
            //вместо добавления - переписываем, чтоб они не накладывались 2 раз. Задний не работает
            .replace(R.id.container, LoginFragment.newInstance(""))
            .commit()
    }

}

