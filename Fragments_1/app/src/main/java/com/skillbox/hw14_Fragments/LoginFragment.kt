package com.skillbox.hw14_Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var binding: FragmentLoginBinding

    //    variable for bundle
    private var state = FormState(false, "")

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        //      ANR
        binding.buttonAnr.setOnClickListener {
            Handler().postDelayed({
                toast("ooppps, something wrong")
            }, 2000)
            Thread.sleep(10000)
        }

//      checking correct login or email
        binding.button.setOnClickListener {
            if (binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()
                && isValidEmail()
            ) {
                binding.validationText.text = "You Entered valid Email and password"
                toast("The Pentagon is crashed")
                makeProgressBar()
            } else binding.validationText.text = "Error: enter valid Email"
        }

        emailPasswordFieldCheck()

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

    private fun emailPasswordFieldCheck() {
        listOf(binding.inputEmail, binding.inputPassword).forEach {
            it.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    checkButton()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
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

    //   put in bundle
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        val state = FormState(binding.button.isEnabled, binding.validationText.text.toString())
//        outState.putParcelable(KEY_STATE, state)
//    }
//
//    //   put out bundle and refresh View
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        state = savedInstanceState.getParcelable(KEY_STATE) ?: FormState(false, "")
//        binding.button.isEnabled = state.valid
//        binding.validationText.text = state.message
//    }
//
    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val KEY_TEXT = "key text"

        fun newInstance(text: String): LoginFragment {
            return LoginFragment().withArguments {
                putString(KEY_TEXT, text)
            }
        }
    }

}
