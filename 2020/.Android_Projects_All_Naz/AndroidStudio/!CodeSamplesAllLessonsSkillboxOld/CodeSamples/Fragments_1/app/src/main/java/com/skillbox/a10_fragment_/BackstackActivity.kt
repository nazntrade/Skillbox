package com.skillbox.fragment10

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.skillbox.a10_fragment_.InfoFragment
import com.skillbox.a10_fragment_.R
import kotlinx.android.synthetic.main.activity_backstack.*

class BackstackActivity : AppCompatActivity(R.layout.activity_backstack) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
                .add(R.id.container, InfoFragment.newInstance("text"))
            if (needAddToBackstack()) {
                transaction.addToBackStack(getStateName())
            }
            transaction.commit()
        }
        replaceButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, InfoFragment.newInstance("text"))
                .apply {
                    if (needAddToBackstack()) addToBackStack(getStateName())
                }
                .commit()
        }

        popbackstackButton.setOnClickListener {
            val stateName = getStateName()
            if (stateName != null) {
                supportFragmentManager.popBackStack(stateName, 0)
            } else {
                supportFragmentManager.popBackStack()
            }
        }

        addBackstackCheckbox.setOnCheckedChangeListener { _, isChecked ->
            stateNameInput.visibility = if(isChecked) View.VISIBLE else View.GONE
        }

    }

    override fun onBackPressed() {

    }

    private fun getStateName(): String? {
        return stateNameInput.text.toString().trim().takeIf { it.isNotBlank() }
    }

    private fun needAddToBackstack(): Boolean {
        return addBackstackCheckbox.isChecked
    }
}