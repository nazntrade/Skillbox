package com.skillbox.activity09

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_email.*
import kotlinx.android.synthetic.main.activity_main.*

class EmailActivity: AppCompatActivity(R.layout.activity_email) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEmailParamsFromIntent()
    }

    private fun setEmailParamsFromIntent() {
        val addesses = intent.getStringArrayExtra(Intent.EXTRA_EMAIL)
        val subject = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        addressTextView.text = addesses?.joinToString() ?: "Email address is not set"
        subjectTextView.text = subject ?: "Subject is not set"

    }

}