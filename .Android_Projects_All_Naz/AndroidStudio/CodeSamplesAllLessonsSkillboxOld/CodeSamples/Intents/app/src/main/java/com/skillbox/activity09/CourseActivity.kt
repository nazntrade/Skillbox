package com.skillbox.activity09

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course.*

class CourseActivity: AppCompatActivity(R.layout.activity_course) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hanleIntentData()
    }

    // https://skillbox.ru/course/profession-android-developer/
    private fun hanleIntentData() {
        intent.data?.lastPathSegment?.let { courseName ->
            courseNameTextView.text = courseName
        }
    }

}