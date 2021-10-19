package com.skillbox.a10_fragment_

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragmentButton.setOnClickListener {
            showInfoFragment()
        }

        replaceFragmentButton.setOnClickListener {
            replaceInfoFragment()
        }
        Log.d("lifecycle", "MainActivity onCreate, ${hashCode()}")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("lifecycle", "MainActivity onRestoreInstanceState, ${hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "MainActivity onStart, ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "MainActivity onResume, ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "MainActivity onPause, ${hashCode()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("lifecycle", "MainActivity onSaveInstanceState, ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "MainActivity onStop, ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "MainActivity onDestroy, ${hashCode()}")

        if(!supportFragmentManager.isStateSaved) {
            showInfoFragment()
        }
    }

    private fun showInfoFragment() {

        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null

        if(!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, InfoFragment.newInstance(dataEditText.text.toString()))
                .commitAllowingStateLoss()
        } else {
            toast("Fragment is shown")
        }
    }

    private fun replaceInfoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InfoFragment.newInstance(dataEditText.text.toString()))
            .commit()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
