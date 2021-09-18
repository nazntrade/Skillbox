package com.skillbox.homework12_12_Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.*
import com.skillbox.homework12_12_Activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    lateinit var binding: ActivityMainBinding

    private fun isValidEmail (): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString()).matches()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DebugLogger.e(tag, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
//      ANR
        binding.buttonAnr.setOnClickListener {
            Thread.sleep(10000)
            Toast.makeText(this, "ooppps", Toast.LENGTH_LONG).show()
        }

//      checking correct login or email
        binding.button.setOnClickListener {
            makeProgressBar()
            if (binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()
                && isValidEmail()
            ) {
                binding.validationText.text = "You Entered valid Email and password"
                Toast.makeText(this, "The Pentagon is crashed", Toast.LENGTH_LONG).show()
            } else binding.validationText.text = "Error: enter valid Email"
        }

        binding.inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkButton()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.inputPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkButton()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.checkbox.setOnClickListener {
            checkButton()
        }
    }

    fun checkButton() {
        binding.button.isEnabled =
            binding.inputEmail.text.isNotEmpty()
                    && binding.inputPassword.text.isNotEmpty()
                    && binding.checkbox.isChecked
    }

    private fun makeProgressBar() {
        val view = layoutInflater.inflate(R.layout.progres_bar, binding.container, false)
        view.apply {
            binding.container.addView(view)
        }
//        binding.progressBar.visibility = View.VISIBLE
        binding.button.isEnabled = false
        binding.inputEmail.isEnabled = false
        binding.inputPassword.isEnabled = false
        binding.checkbox.isEnabled = false

        Handler().postDelayed({
//            binding.progressBar.visibility = View.GONE
            binding.button.isEnabled = true
            binding.inputEmail.isEnabled = true
            binding.inputPassword.isEnabled = true
            binding.checkbox.isEnabled = true
            binding.container.removeView(view)
        }, 2000)
    }
//  LifeCycles activity
    override fun onStart() {
        super.onStart()
        DebugLogger.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.i(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        DebugLogger.w(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        DebugLogger.e(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.v(tag, "onDestroy")
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable()
//    }
}

object DebugLogger {
    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }
    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }
    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }
}

