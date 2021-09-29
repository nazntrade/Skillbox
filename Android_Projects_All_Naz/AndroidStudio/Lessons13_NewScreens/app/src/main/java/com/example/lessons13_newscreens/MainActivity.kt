package com.example.lessons13_newscreens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.lessons13_newscreens.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

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

        //на нек.устр.необх.обяз разреш для исп камеры


        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        check camera app
        cameraIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(cameraIntent, PHOTO_REQUEST_CODE)
        }
    }

    //    обработать рез., устан.изобр в ImageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PHOTO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val previewBitmap = data?.getParcelableExtra("data") as? Bitmap
                binding.resultPhotoImageView.setImageBitmap(previewBitmap)
            } else {
                toast("Photo capture was cancelled")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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