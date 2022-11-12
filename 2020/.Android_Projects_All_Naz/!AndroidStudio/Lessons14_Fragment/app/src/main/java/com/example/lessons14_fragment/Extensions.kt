package com.example.lessons14_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

//in this file we'll be to put extensions for using in all places


//Lesson14.4 - t.30.00
fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        val args: Bundle = Bundle().apply(action)
        arguments = args
    }
}