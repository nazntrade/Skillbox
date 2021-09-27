package com.example.lessons13_newscreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons13_newscreens.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEmailParamsFromIntent()
    }

    private fun setEmailParamsFromIntent() {
        val addresses = intent.getStringArrayExtra(Intent.EXTRA_EMAIL)
        val subject = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        binding.addressTextView.text = addresses?.joinToString() ?: "Email address is not set"
        binding.subjectTextView.text = subject ?: "Subject is not set"
    }
}

