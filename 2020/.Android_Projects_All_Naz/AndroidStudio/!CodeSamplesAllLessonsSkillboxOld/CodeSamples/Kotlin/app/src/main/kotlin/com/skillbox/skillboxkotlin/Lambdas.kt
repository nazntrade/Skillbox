package com.skillbox.skillboxkotlin


fun main() {

    val lambda = { println("inside lambda") }
    lambda()

    println({ x: Int, y: Int -> x + y }(1, 2))

    val users = listOf(
        User("Василий", "Петров", 20),
        User("Сергей", "Иванов", 30),
        User("Иван", "Сергеев", 10),
        User("Евгений", "Васильев", 50)
    )

    var oldestUser: User? = null
    for (user in users) {
        val userAge = user.age
        val maxAge = oldestUser?.age ?: Int.MIN_VALUE
        if (userAge > maxAge) {
            oldestUser = user
        }
    }

    fun getCallback(): (Int) -> Int {
        return { 5 + it }
    }
}
