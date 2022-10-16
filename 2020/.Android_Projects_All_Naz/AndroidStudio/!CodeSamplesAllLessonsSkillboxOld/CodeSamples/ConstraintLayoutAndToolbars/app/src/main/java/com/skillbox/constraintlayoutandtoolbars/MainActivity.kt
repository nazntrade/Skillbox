package com.skillbox.constraintlayoutandtoolbars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            group.isVisible = group.isVisible.not()
        }

        group.referencedIds.forEach {
            findViewById<View>(it).setOnClickListener {
                Toast.makeText(this, "111", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
