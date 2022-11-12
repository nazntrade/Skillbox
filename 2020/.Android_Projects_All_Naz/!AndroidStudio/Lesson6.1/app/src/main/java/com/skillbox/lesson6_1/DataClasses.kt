package com.skillbox.lesson6_1

fun main() {
    val user1 = User("name1", "lastName1")
    user1.innerState = "state1"
    val user2 = User("name1", "lastName1")
    user2.innerState = "state2"

    println(user1 == user2)

    val user3 = user1.copy(lastName = "copiedLastName")
    println("user3 inner state = ${user3.innerState}")
    println(user1)

    val (name, lastName) = User("name5", "lastName5")
    println("name = ${name}")
    println("lastName = ${lastName}")

    val users = listOf(
        user1,
        user2,
        user3
    )

    users.forEach { (a, b) -> println("$a $b") }

    val map = mapOf(
        1 to "1",
        2 to "2"
    )
    for ((key, value) in map) {
        key
        value
    }
}