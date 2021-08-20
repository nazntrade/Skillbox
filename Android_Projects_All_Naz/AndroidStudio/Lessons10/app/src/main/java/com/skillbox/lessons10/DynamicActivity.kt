package com.skillbox.lessons10

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DynamicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        // объявляю и нахожу все, к чему хочу получить доступ из активити
        val addButton = findViewById<Button>(R.id.addButton)
        val textInput = findViewById<TextView>(R.id.textInput)

        // а потом уже все это использую
//        findViewById нужно делать перед каждым использованием, причем, когда я это сделал в начале
//        программа на телефоне не работала. т.е нужно добавлять именно там, где он будет использоваться

        addButton.setOnClickListener {
            val textToAdd = textInput.text.toString()
            val container = findViewById<LinearLayout>(R.id.container)
//динамич.добавление и удаление View
            val view = layoutInflater.inflate(R.layout.item_text, container, false)
            view.apply {
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = textToAdd
                val deleteButton = findViewById<Button>(R.id.deleteButton)
                deleteButton.setOnClickListener {
                    container.removeView(this)
                }
            }
            container.addView(view)
        }
    }
}
