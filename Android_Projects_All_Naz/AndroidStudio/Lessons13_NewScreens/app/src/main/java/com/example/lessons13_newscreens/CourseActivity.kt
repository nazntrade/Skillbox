package com.example.lessons13_newscreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons13_newscreens.databinding.ActivityCourseBinding

class CourseActivity : AppCompatActivity() {
    lateinit var binding: ActivityCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntentData()
    }

    //https://skillbox.ru/course/profession-android-developer-2021/
    private fun handleIntentData() {
        intent.data?.lastPathSegment?.let { courseName ->
            binding.courseNameTextView.text = courseName
        }
    }

}