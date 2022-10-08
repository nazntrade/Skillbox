package com.skillbox.skillboxkotlin


fun main() {

    listOf(1, 2, 3) + listOf(4, 5, 6)

    val rectangle1 = Rectangle(100, 200, 10, 1)
    val rectangle2 = Rectangle(10, 2, 10, 1)

    println(rectangle1 + rectangle2)
    println(-rectangle1)
}
