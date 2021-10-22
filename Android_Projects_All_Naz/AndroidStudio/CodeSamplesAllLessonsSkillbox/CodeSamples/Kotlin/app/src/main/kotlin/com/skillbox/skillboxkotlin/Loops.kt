package com.skillbox.skillboxkotlin


fun main() {
    print("Введите число: ")
    val n = readLine()?.toIntOrNull() ?: return

    println("while: ${calculateSumByWhile(n)}")
    println("while true: ${calculateSumByWhileTrue(n)}")
    println("do while: ${calculateSumByDoWhile(n)}")
    println("for step 1: ${calculateSumByForStep1(n)}")
    println("for step 2: ${calculateSumByForStep2(n)}")

//    printEvenNumberWhileContinue(n)
    printByForStep2(n)
}

fun calculateSumByWhile(n: Int): Int {
    var currentSum = 0
    var currentNumber = 0
    while (currentNumber <= n) {
        currentSum += currentNumber
        currentNumber++
    }
    return currentSum
}

fun calculateSumByWhileTrue(n: Int): Int {
    var currentSum = 0
    var currentNumber = 0
    while (true) {
        if (currentNumber > n) return currentSum
        currentSum += currentNumber
        currentNumber++
    }
}

fun printEvenNumberWhileContinue(n: Int) {
    var currentNumber = 0
    while (currentNumber <= n) {
        val numberBefore = currentNumber
        currentNumber++
        if (numberBefore % 2 == 1) continue
        println(numberBefore)
    }
}

fun calculateSumByDoWhile(n: Int): Int {
    var currentSum = 0
    var currentNumber = 0
    do {
        currentSum += currentNumber
        currentNumber++
    } while (currentNumber <= n)
    return currentSum
}

fun calculateSumByForStep1(n: Int): Int {
    var currentSum = 0
    val range = 0..n
    for (i in range) {
        currentSum += i
    }
    return currentSum
}

fun calculateSumByForStep2(n: Int): Int {
    var currentSum = 0
    val range = n downTo 0 step 2
    for (i in range) {
        currentSum += i
    }
    return currentSum
}

fun printByForStep2(n: Int) {
    val range = 0..n step 2
    for (i in range) {
        println(i)
    }
}
