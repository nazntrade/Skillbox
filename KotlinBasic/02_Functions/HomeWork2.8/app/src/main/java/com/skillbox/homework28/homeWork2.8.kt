package com.skillbox.homework28

import kotlin.math.sqrt

/*the equation - a * x^2 + b * x + c = 0*/
fun main() {
    val solutionSum = solveEquation(a = -8, b = 14, c = 73)
    println("root sum is $solutionSum")
}

fun solveEquation(a: Int, b: Int, c: Int): Double? {
//*finding discriminant
    val d = (b * b - 4.0 * a * c)
    if (d < 0) return null

//finding roots

    val x1 = (sqrt(d) - b) / 2.0 * a
    val x2 = (-sqrt(d) - b) / 2.0 * a
    println("""
            x1=${x1}            
            x2=${x2}""")
    return (x1 + x2)
}




