package com.skillbox.homework13_opening_new_screens

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.skillbox.homework13_opening_new_screens.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val callLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                toast("You called the number ${binding.phoneEditText.text}")
            } else toast("No Result")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.callButton.setOnClickListener {
            dispatchCallIntent()
        }
    }

    private fun dispatchCallIntent() {
        val phoneNumber = binding.phoneEditText.text.toString()
        val isPhoneNumberValid = Patterns.PHONE.matcher(phoneNumber).matches()

        //на нек.устр.необх.обяз разреш для исп камеры/проверка разреш
        val isCallPermissionGranted =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        //если разреш нет - запрос разрешения
        if (!isCallPermissionGranted && !isPhoneNumberValid) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                14 //просто интовая константа,кот мы не используем
            )
            toast("Enter valid phone number")

        } else {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            if (callIntent.resolveActivity(packageManager) != null) {
                callLauncher.launch(callIntent)
            } else {
                toast("No component to handle intent")
            }
        }
    }

    // the button 'back' close app
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}
