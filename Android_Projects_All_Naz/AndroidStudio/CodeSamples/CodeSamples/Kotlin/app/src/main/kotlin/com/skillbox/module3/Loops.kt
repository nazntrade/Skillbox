package com.skillbox.module3

fun main() {

    print("Введите число: ")
    val n = readLine()?.toIntOrNull() ?: return

    println("Вы ввели число: $n")

    println("Сумма с помощью while = ${calculateSumByWhile(n)}")
    println("Сумма с помощью while и return = ${calculateSumByWhileInfiniteReturn(n)}")
    println("Сумма с помощью while и break = ${calculateSumByWhileInfiniteBreak(n)}")

//    printEvenNumbers(n)

    println("Сумма с помощью do while = ${calculateSumDoWhile(n)}")
    println("Сумма с помощью for = ${calculateSumFor(n)}")

    printChars()
    printEvenNumberFor(n)
}

fun calculateSumByWhile(n: Int): Long {
    var sum: Long = 0
    var currentNumber: Int = 0
    while (currentNumber <= n) {
        sum += currentNumber
        currentNumber++
    }

    return sum
}

fun calculateSumByWhileInfiniteReturn(n: Int): Long {
    var sum: Long = 0
    var currentNumber: Int = 0

    while (true) {

        if(currentNumber > n) return sum

        sum += currentNumber
        currentNumber++
    }
}

fun calculateSumByWhileInfiniteBreak(n: Int): Long {
    var sum: Long = 0
    var currentNumber: Int = 0

    while (true) {

        if(currentNumber > n) break

        sum += currentNumber
        currentNumber++
    }

    return sum
}

fun printEvenNumbers(n: Int) {
    var currentNumber = 0
    while (currentNumber <= n) {
        val numberBefore = currentNumber
        currentNumber++
        if(numberBefore % 2 == 1) continue
        println(numberBefore)

    }
}

fun calculateSumDoWhile(n: Int): Long {
    var sum: Long = 0
    var currentNumber: Int = 0

    do {
        sum += currentNumber
        currentNumber++
    } while (currentNumber <= n)

    return sum
}

fun calculateSumFor(n: Int): Long {
    var sum: Long = 0

    val range = 0 .. n

    for (currentNumber in range) {
        sum += currentNumber
    }

    return sum
}

fun printChars() {
    for (currentCh in "string") {
        println(currentCh)
    }
}

fun printEvenNumberFor(n: Int) {
    val range = n downTo 0 step 2
    for(currentNumber in range) {
        println(currentNumber)
    }
}