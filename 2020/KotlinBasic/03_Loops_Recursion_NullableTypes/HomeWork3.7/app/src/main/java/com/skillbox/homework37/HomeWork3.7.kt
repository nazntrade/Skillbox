package com.skillbox.homework37

fun main() {
    print("How many numbers do you want to enter? ")
    val quantityNumber = inputInt()
    val sum = enterNumber(quantityNumber)
    // calculate the greatest common divisor
    print("Enter number ")
    val n = inputInt()
    println("the greatest common divisor $sum and $n is ${gcd(sum, n)}")
    println("Bye bye")
}
private tailrec fun inputInt(): Int {
    return readLine()?.toIntOrNull() ?: inputInt()
}
fun enterNumber(quantityNumber: Int): Int {
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
    return sum
}
fun gcd(sum: Int, n: Int): Int {
    return if (n == 0) sum else gcd(n, sum % n)
}
