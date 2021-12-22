package com.example.hw_ViewModelAndNavigation.articles

import android.os.Bundle
import androidx.fragment.app.Fragment

//in this file we'll be to put extensions for using in all places
// чтобы удобно добавлять в фрагменты разные аргументы
//Lesson14.4 - time.28.30
fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        val args: Bundle = Bundle().apply(action)
        arguments = args
    }
}
//write to the future
//fun <T : Activity> T.toast(message: String) {
//    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//}