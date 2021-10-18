package com.skillbox.hw14_Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var binding: FragmentLoginBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

//      checking correct login or email
        binding.buttonLogin.setOnClickListener {
            if (binding.inputPassword.text.isNotEmpty()
                && isValidEmail()
            ) {
                binding.validationText.text = "You Entered valid Email and password"
                makeProgressBar()

                //opening mainActivity//////// Обычный Handler устарел
                Handler(Looper.myLooper()!!) .postDelayed({
                    val mainFragment = MainFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.containerMainActivity, mainFragment)
                        ?.commit()
                }, 3000)
            }
        }

        emailPasswordFieldCheck()

        binding.checkbox.setOnClickListener {
            checkButton()
        }
    }

    private fun checkButton() {
        binding.buttonLogin.isEnabled =
            binding.inputPassword.text.isNotEmpty() && binding.checkbox.isChecked && isValidEmail()
        binding.validationText.text = ""
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
        val view = layoutInflater.inflate(R.layout.progres_bar, binding.containerProgressBar, false)
        view.apply {
            binding.containerProgressBar.addView(view)
        }
        binding.buttonLogin.isEnabled = false
        binding.inputEmail.isEnabled = false
        binding.inputPassword.isEnabled = false
        binding.checkbox.isEnabled = false
        Handler(Looper.myLooper()!!).postDelayed({
            binding.buttonLogin.isEnabled = true
            binding.inputEmail.isEnabled = true
            binding.inputPassword.isEnabled = true
            binding.checkbox.isEnabled = true
            binding.containerProgressBar.removeView(view)
        }, 2000)
    }

    private fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString()).matches()
    }

}
