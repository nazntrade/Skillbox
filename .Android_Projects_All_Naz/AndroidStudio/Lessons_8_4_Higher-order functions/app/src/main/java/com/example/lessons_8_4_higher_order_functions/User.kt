package com.example.lessons_8_4_higher_order_functions

data class User(
    val name: String,
    val lastName: String,
    val age: Int = 0
) {
    fun getFullnameLength() = "$name$lastName".length

}