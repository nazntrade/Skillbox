package com.skillbox.activity09

import android.Manifest
import android.app.Activity
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
import androidx.activity.invoke
import androidx.activity.prepareCall
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //https://skillbox.ru/course/profession-android-developer/
    //https://skillbox.ru/course/weblayout/

    private val cameraLauncher = prepareCall(ActivityResultContracts.TakePicture()) { bitmap ->
        bitmap ?: toast("Photo capture was cancelled")
        resultPhotoImageView.setImageBitmap(bitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("LifecycleTest", "MainActivity|onCreate|${hashCode()}")

        startExplicitButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            startActivity(SecondActivity.getIntent(this, messageText))
        }

        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        sendEmailButton.setOnClickListener {
            dispatchEmailIntent()
        }
    }

    private fun dispatchTakePictureIntent() {

        val isCameraPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

        if(!isCameraPermissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 14)
        } else {
            cameraLauncher()
        }
    }

    private fun dispatchEmailIntent() {
        val emailAddress = emailAddressEditText.text.toString()
        val emailSubject = subjectEditText.text.toString()

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()

        if(!isEmailValid) {
            toast("Enter valid email address")
        } else {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            }

            if(emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                toast("No component to handle intent")
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == PHOTO_REQUEST_CODE) {
//            if(resultCode == Activity.RESULT_OK) {
//                val previewBitmap = data?.getParcelableExtra("data") as? Bitmap
//                resultPhotoImageView.setImageBitmap(previewBitmap)
//            } else {
//                toast("Photo capture was cancelled")
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

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
