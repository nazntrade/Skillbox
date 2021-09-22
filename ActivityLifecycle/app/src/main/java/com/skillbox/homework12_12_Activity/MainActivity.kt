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
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    lateinit var binding: ActivityMainBinding

    //    variable for bundle
    private var state = FormState(false, "")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DebugLogger.e(tag, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //      ANR
        binding.buttonAnr.setOnClickListener {
            Handler().postDelayed({
                Toast.makeText(this, "ooppps, something wrong", Toast.LENGTH_LONG).show()
            }, 2000)
            Thread.sleep(10000)
        }

//      checking correct login or email
        binding.button.setOnClickListener {
            if (binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()
                && isValidEmail()
            ) {
                binding.validationText.text = "You Entered valid Email and password"
                Toast.makeText(this, "The Pentagon is crashed", Toast.LENGTH_LONG).show()
                makeProgressBar()
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

    private fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString()).matches()
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

    //   put in bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val state = FormState(binding.button.isEnabled, binding.validationText.text.toString())
        outState.putParcelable(KEY_STATE, state)
    }

    //   put out bundle and refresh View
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getParcelable(KEY_STATE) ?: FormState(false, "")
//        Toast.makeText(this, "Value: $state", Toast.LENGTH_SHORT).show()
        binding.button.isEnabled = state.valid
        binding.validationText.text = state.message
    }

    companion object {
        private const val KEY_STATE = "state"
    }
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

