package com.skillbox.module3

import kotlin.random.Random

fun main() {

    calculateRandomInt()
        .takeIf { it > 4 }
        ?.let { print("number is greater than 4") }

}

fun calculateRandomInt() = Random.nextInt()