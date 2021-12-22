package com.skillbox.helloworld

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val textView = findViewById<TextView>(R.id.text)
         val count = 5
         textView.text = resources.getQuantityString(R.plurals.main_quantity_string, count, count)
         textView.text = """
             |Build type = ${BuildConfig.BUILD_TYPE}
             |Flavor = ${BuildConfig.FLAVOR}
             |VesionCode = ${BuildConfig.VERSION_CODE}
             |VersionName=${BuildConfig.VERSION_NAME}"""
             .trimMargin()
    }
}