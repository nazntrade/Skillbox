package com.skillbox.lesson6_1

data class User(
    val name: String,
    val lastName: String
) {
    var innerState: String = ""
}