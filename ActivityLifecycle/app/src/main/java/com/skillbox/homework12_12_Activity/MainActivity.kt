package com.skillbox.homework12_12_Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import com.skillbox.homework12_12_Activity.databinding.ActivityMainBinding

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
            checkButton()
        }
        binding.inputPassword.setOnClickListener {
            checkButton()
        }
        binding.checkbox.setOnClickListener {
            checkButton()
        }
    }

    fun checkButton() {
        binding.button.isEnabled =
            binding.inputEmail.text.isNotEmpty()
                    && binding.inputPassword.text.isNotEmpty()
                    && binding.checkbox.isChecked
    }

    private fun makeProgressBar() {
        val view = layoutInflater.inflate(R.layout.progres_bar, binding.container, false)
        view.apply {
            binding.container.addView(view)
        }
//        binding.progressBar.visibility = View.VISIBLE
        binding.button.isEnabled = false
        binding.inputEmail.isEnabled = false
        binding.inputPassword.isEnabled = false
        binding.checkbox.isEnabled = false

        Handler().postDelayed({
//            binding.progressBar.visibility = View.GONE
            binding.button.isEnabled = true
            binding.inputEmail.isEnabled = true
            binding.inputPassword.isEnabled = true
            binding.checkbox.isEnabled = true
            binding.container.removeView(view)
            Toast.makeText(this, R.string.you_are_logged_in, Toast.LENGTH_LONG).show()
        }, 2000)
    }
}
