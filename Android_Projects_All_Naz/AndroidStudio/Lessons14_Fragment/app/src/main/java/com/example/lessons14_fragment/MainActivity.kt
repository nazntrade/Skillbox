package com.example.lessons14_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lessons14_fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showFragmentButton.setOnClickListener {
            showInfoFragment()
        }

        binding.replaceFragmentButton.setOnClickListener {
            replaceInfoFragment()
        }

    }

    private fun showInfoFragment() {
        //проверим есть ли в контейнере уже фрагмент, чтоб не лепить один на другой
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null

        //для динамической работы с фрагментами нужен supportFragmentManager
        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                //добавляем (к какому, какой) и они работают вместе параллельно
                .add(R.id.container, InfoFragment.newInstance(binding.dataEditText.text.toString()))
                .commit()
        } else {
            toast("Fragment is shown")
        }
    }

    private fun replaceInfoFragment() {
        supportFragmentManager.beginTransaction()
            //вместо добавления - переписываем, чтоб они не накладывались 2 раз. задний не работает
            .replace(R.id.container, InfoFragment.newInstance(binding.dataEditText.text.toString()))
            .commit()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
