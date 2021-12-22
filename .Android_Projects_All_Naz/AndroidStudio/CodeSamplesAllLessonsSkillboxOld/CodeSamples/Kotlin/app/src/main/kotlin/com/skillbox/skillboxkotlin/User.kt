package com.skillbox.skillboxkotlin


data class User(
    val firstName: String,
    val secondName: String,
    val age: Int
) {
    fun getFullNameLength(): Int = "$firstName$secondName".length
}
