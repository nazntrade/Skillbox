@file:Suppress("UNREACHABLE_CODE")

package com.skillbox.homework37

fun main() {
    print("How many numbers do you want to enter? ")
    val quantityNumber = inputInt()
    val sum = enterNumber(quantityNumber)

    print("Enter number ")
    val b = inputInt()
    val gcd = (sum % b)
    return if (b == 0) sum
    else gcd

    print("GCD is $gcd")

}
private tailrec fun inputInt(): Int {return readLine()?.toIntOrNull() ?: inputInt()}

fun enterNumber(quantityNumber: Int) {
    var sum = 0
    var posCount = 0
    repeat(quantityNumber) {
        print("Enter $it number ")
        val number = inputInt()
        sum += number
        if (number > 0) posCount++
    }
    println("The quantity positive numbers is $posCount")
    println("The sum of the numbers is $sum")

}


