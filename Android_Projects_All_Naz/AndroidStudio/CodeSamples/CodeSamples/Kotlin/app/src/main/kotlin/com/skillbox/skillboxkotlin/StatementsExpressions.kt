package com.skillbox.skillboxkotlin

import kotlin.random.Random

fun main() {
    val b: Int = 10

    val whenResult = when (b) {
        in 1..10 -> println("from 1 to 10")
        in 20..30 -> println("from 20 to 30")
        else -> println("else")
    }

    calculateRandomInt()
        .let { it > 0 }
        .takeIf { true }
        ?.let { println("true") }
        ?.let { /*Another action*/ }
}

fun calculateRandomInt(): Int {
    return Random.nextInt()
}
