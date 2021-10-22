package com.skillbox.skillboxkotlin

fun main() {

    val users = arrayOf("Петров", null, "Сидоров") + arrayOf("@@@")
    for (user in users) {
        println(user)
    }

    users.forEach { println(it) }

    users.forEachIndexed { index, item -> println("User #$index $item") }

    val firstUser = users[0]
    val lastUser = users[users.lastIndex]

//    val incorrectUser = users[1000]

    println("first user = $firstUser")
    println("last user = $lastUser")

    users[0] = "Новый первый пользователь"
    users.forEachIndexed { index, item -> println("User #$index $item") }

    val emptyUsers = emptyArray<String>()

    Array(1000) { "User #$it" }
}
