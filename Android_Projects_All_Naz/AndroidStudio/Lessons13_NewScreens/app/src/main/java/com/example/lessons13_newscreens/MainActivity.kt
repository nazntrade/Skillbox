package com.example.lessons13_newscreens

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lessons13_newscreens.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    // в уроке этот prepareCall метод! он больше не существует
//      prepareCall(ActivityResultContracts.TakePicture()) {
//                  bitmap ?: toast("Photo capture was cancelled")
//            binding.resultPhotoImageView.setImageBitmap(bitmap)}
//        вместо него новый (с добавл.в gradle)
    // лончер нужно создавать именно тут, чтоб его можно было вернуть после паузы и стоп
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
            if (result != null) {
                toast("Result : $result")
                binding.resultPhotoImageView.setImageBitmap(result)
            }
            else toast("No Result")
        }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("LifecycleTest", "MainActivity|onCreate|${hashCode()}")

        binding.startExplicitButton.setOnClickListener {
            val messageText = binding.messageEditText.text.toString()
            startActivity(SecondActivity.getIntent(this, messageText))
        }

        binding.takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.sendEmailButton.setOnClickListener {
            val emailAddress = binding.emailAddressEditText.text.toString()
            val emailSubject = binding.subjectEditText.text.toString()
            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
            if (!isEmailValid) {
                toast("Enter valid email address")
            } else {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:") // only email apps should handle this
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                }

//                startActivity(emailIntent)

                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(emailIntent)
                } else {
                    toast("No component to handle intent")
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        //на нек.устр.необх.обяз разреш для исп камеры/проверка разреш
        val isCameraPermissionGranted =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        //если разреш нет - запрос разрешения
        if (!isCameraPermissionGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                14 //просто интовая константа,кот мы не используем
            )
        } else {
            cameraLauncher.launch(null) //упростили благодаря библиотеке ktx
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifecycleTest", "MainActivity|onStart|${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifecycleTest", "MainActivity|onResume|${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifecycleTest", "MainActivity|onPause|${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifecycleTest", "MainActivity|onStop|${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleTest", "MainActivity|onDestroy|${hashCode()}")
    }

    companion object {
        private const val PHOTO_REQUEST_CODE = 123
    }
}