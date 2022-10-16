package com.skillbox.skillboxkotlin


fun main() {
    println(calculateSum(50000000))
}

tailrec fun calculateSum(n: Int, accum: Int = 0): Int {
    return if (n <= 0) {
        accum
    } else {
        calculateSum(n - 1, accum + n)
    }
}
