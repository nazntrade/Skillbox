package com.skillbox.homework10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.Button)
        button.setOnClickListener {
            makeProgressBar()
        }
        val inputEmail = findViewById<EditText>(R.id.InputEmail)
        inputEmail.setOnClickListener {
            checkButtonOn()
        }
        val inputPassword = findViewById<EditText>(R.id.InputPassword)
        inputPassword.setOnClickListener {
            checkButtonOn()
        }
        val checkbox = findViewById<CheckBox>(R.id.checkbox)
        checkbox.setOnClickListener {
            checkButtonOn()
        }

    }

    fun checkButtonOn() {
        val button = findViewById<Button>(R.id.Button)
        val inputEmail = findViewById<EditText>(R.id.InputEmail)
        val inputPassword = findViewById<EditText>(R.id.InputPassword)
        val checkbox = findViewById<CheckBox>(R.id.checkbox)

        button.isEnabled =
            inputEmail.text.isNotEmpty() && inputPassword.text.isNotEmpty() && checkbox.isChecked
    }

    fun makeProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val button = findViewById<Button>(R.id.Button)
        button.isEnabled = false
        val inputEmail = findViewById<EditText>(R.id.InputEmail)
        inputEmail.isEnabled = false
        val inputPassword = findViewById<EditText>(R.id.InputPassword)
        inputPassword.isEnabled = false
        val checkbox = findViewById<CheckBox>(R.id.checkbox)
        checkbox.isEnabled = false

        Handler().postDelayed({
            progressBar.visibility = View.GONE
            button.isEnabled = true
            inputEmail.isEnabled = true
            inputPassword.isEnabled = true
            checkbox.isEnabled = true
            Toast.makeText(this, R.string.you_are_logged_in,
                Toast.LENGTH_LONG)
                .show()
        }, 2000)
    }
}
