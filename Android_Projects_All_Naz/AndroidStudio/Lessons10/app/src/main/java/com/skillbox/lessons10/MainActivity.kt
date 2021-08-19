package com.skillbox.lessons10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)

        val nameText = findViewById<TextView>(R.id.nameText)

        val clearButton = findViewById<Button>(R.id.clearButton)

        clearButton.setOnClickListener {
            nameInput.setText("")
            Toast.makeText(this, R.string.cleared_text, Toast.LENGTH_SHORT).show()
        }

        nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameText.text = p0?.takeIf { it.isNotBlank() }
                    ?.let { name -> resources.getString(R.string.hello_string, name) }

                clearButton.isEnabled = p0?.let { it.isNotEmpty() } ?: false
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        val makeLongOperationButton = findViewById<Button>(R.id.makeLongOperationButton)
        makeLongOperationButton.setOnClickListener {
            makeLongOperation()
        }
    }

    private fun makeLongOperation() {
        val longOperationProgress = findViewById<ProgressBar>(R.id.longOperationProgress)
        longOperationProgress.visibility = View.VISIBLE

        val makeLongOperationButton = findViewById<Button>(R.id.makeLongOperationButton)
        makeLongOperationButton.isEnabled = false

        Handler().postDelayed({
            longOperationProgress.visibility = View.GONE
            makeLongOperationButton.isEnabled = true
            Toast.makeText(this, R.string.long_operation_completed, Toast.LENGTH_SHORT).show()
        }, 2000)

    }
}