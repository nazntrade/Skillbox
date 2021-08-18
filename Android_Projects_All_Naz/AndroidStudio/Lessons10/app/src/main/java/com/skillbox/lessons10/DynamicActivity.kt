package com.skillbox.lessons10

import android.os.Bundle
import android.view.Gravity
import android.view.View
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
        val textView = findViewById<TextView>(R.id.textView)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        //
        // а потом уже все это использую

        addButton.setOnClickListener {
            val textToAdd = textInput.text.toString()
            val view = layoutInflater.inflate(R.layout.item_text, container, false)
            view.apply {
                textView.text = textToAdd
                deleteButton.setOnClickListener {
                    container.removeView()
                }
            }
        }
    }
}
