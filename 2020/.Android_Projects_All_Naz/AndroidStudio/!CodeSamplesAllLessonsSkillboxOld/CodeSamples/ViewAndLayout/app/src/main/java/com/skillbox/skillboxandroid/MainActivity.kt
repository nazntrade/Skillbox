package com.skillbox.skillboxandroid

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clearButton.setOnClickListener {
            nameInput.setText("")
            Toast.makeText(this, "Текст был очищен", Toast.LENGTH_SHORT).show()
        }

        makeLongOperationButton.setOnClickListener { makeLongOperation() }

        nameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                helloTextView.visibility = s?.takeIf { it.isNotBlank() }?.let { View.VISIBLE } ?: View.GONE
                helloTextView.text = getString(R.string.hello_template, s)
                clearButton.isEnabled = s?.isNotEmpty() ?: false
            }
        })
    }

    private fun makeLongOperation() {
        makeLongOperationButton.isEnabled = false
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            makeLongOperationButton.isEnabled = true
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Данные загружены", Toast.LENGTH_SHORT).show()
        }, 2000)
    }

}

