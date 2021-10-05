package com.skillbox.homework13_opening_new_screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.homework13_opening_new_screens.databinding.ActivityForumBinding

class ForumActivity : AppCompatActivity() {
    lateinit var binding: ActivityForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        https://stackoverflow.com/questions/69431817/boost-signals2-return-lambda-from-functor-object

        handleIntentData()
    }

    private fun handleIntentData() {
        intent.data?.lastPathSegment?.let { forumName ->
            binding.textUriView.text = forumName
        }
    }
}