package com.skillbox.homework4

import android.graphics.Insets.add

fun main() {
    println("How many phone numbers do you want to enter: ")
    val n = readLine()?.toIntOrNull() ?: return
    val createMutableList = enterMutableList(n)

    println("Created list contains phone numbers: ")
    createMutableList.forEach { println(it) }

    println("Phone numbers starting with '+7'")
    val containsNumber = createMutableList.filter { it.startsWith(prefix = "+7") }
    containsNumber.forEach { println(it) }

    println("Unique phone numbers: ")
    val uniqueNumbers = createMutableList.toSet()
    uniqueNumbers.forEach { println(it) }

    println("Quantity unique phone numbers is ${uniqueNumbers.size}")

    println("The sum of the lengths of all numbers is ${createMutableList.sumBy { it.length }} ")

    val phoneBookMap = mutableMapOf<String, String>()
    uniqueNumbers.forEach { it ->
        println("Enter name for phone number $it: ")
        val name: String? = readLine()
        if (name != null) {
            phoneBookMap[it] = name
        }
    }
    phoneBookMap.forEach { (key, value) ->
        println("Human: $value. Phone number: $key")
    }
}

fun enterMutableList(n: Int): MutableList<String> {
    val enterNumber = mutableListOf<String>()
    repeat(n) {
        println("Enter $it phone number")
        val number: String? = readLine()
        if (number != null) enterNumber.add(it, number)
    }
    return enterNumber
}