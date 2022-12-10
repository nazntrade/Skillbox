package com.example.core.tools.extensions

import android.content.Context
import androidx.fragment.app.Fragment


fun Fragment.checkFirstStart(): Boolean {
    val pref = requireContext().getSharedPreferences("Pref_firstStart", Context.MODE_PRIVATE)

    val a = pref.contains("isFirstStart")

    if (!a) pref.edit().putBoolean("isFirstStart", false).apply()
    return !a
}


