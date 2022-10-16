package com.skillbox.skillboxandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_dynamic.*
import kotlinx.android.synthetic.main.item_text.view.*


class DynamicAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dynamic)

        addButton.setOnClickListener {
            val textToAdd = textInput.text
            val view = layoutInflater.inflate(R.layout.item_text, container, false).apply {
                textView.text = textToAdd
                removeButton.setOnClickListener {
                    container.removeView(this)
                }
            }
            container.addView(view)
        }
    }
}
