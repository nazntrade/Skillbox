package com.skillbox.lessons10

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import kotlin.random.Random

class DynamicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

// объявляю и нахожу все, к чему хочу получить доступ из активити
        val addButton = findViewById<Button>(R.id.addButton)
        val textInput = findViewById<TextView>(R.id.textInput)
        val container = findViewById<LinearLayout>(R.id.container)
        //
        // а потом уже все это использую

        addButton.setOnClickListener {
            val textToAdd = textInput.text.toString()
            val textViewToAdd = TextView(this).apply {
                text = textToAdd
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = when (Random.nextInt(3)) {
                        0 -> Gravity.CENTER
                        1 -> Gravity.END
                        else -> Gravity.START
                    }
                }
            }

            container.addView(textViewToAdd)

        }
    }
}