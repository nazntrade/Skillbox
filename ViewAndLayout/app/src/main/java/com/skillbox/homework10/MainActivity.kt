package com.skillbox.homework10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.skillbox.homework10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            makeProgressBar()
        }
        binding.inputEmail.setOnClickListener {
            checkButtonOn()
        }
        binding.inputPassword.setOnClickListener {
            checkButtonOn()
        }
        binding.checkbox.setOnClickListener {
            checkButtonOn()
        }

    }

    fun checkButtonOn() {
        binding.button.isEnabled =
            binding.inputEmail.text.isNotEmpty()
                    && binding.inputPassword.text.isNotEmpty()
                    && binding.checkbox.isChecked
    }

    fun makeProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.button.isEnabled = false
        binding.inputEmail.isEnabled = false
        binding.inputPassword.isEnabled = false
        binding.checkbox.isEnabled = false

        Handler().postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.button.isEnabled = true
            binding.inputEmail.isEnabled = true
            binding.inputPassword.isEnabled = true
            binding.checkbox.isEnabled = true
            Toast.makeText(
                this, R.string.you_are_logged_in,
                Toast.LENGTH_LONG
            )
                .show()
        }, 2000)
    }
}
