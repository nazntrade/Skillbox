package com.example.textbook_lambda_higher_order_functions

import android.widget.SimpleCursorAdapter

fun main(args: Array<String>) {
//1    var addFive = { x: Int -> x + 5 }
//1    println("Pass 6 to addFive: ${addFive(6)}")
//1
//1    val addInts = { x: Int, y: Int -> x + y }
//1    val result = addInts.invoke(6, 7)
//1    println("Pass 6, 7 to addInts: $result")
//1
//1    val intLambda: (Int, Int) -> Int = { x, y -> x + y }
//1    println("Pass 10, 11 to intLambda: ${intLambda(10, 11)}")
//1
//1    val addSeven: (Int) -> Int = { it + 7 }
//1    println("Pass 12 to addSeven: ${addSeven(12)}")
//1
//1    val myLambda: () -> Unit = { println("Hi") }
//1    myLambda()

//2    convert(20.0) { it * 1.8 + 32 }
//2    convertFive { it * 1.8 + 32 }

    //Преобразовать 2.5 кг в фунты
    println("Convert 2.5kg to Pounds: ${getConversionLambda("KgsToPounds")(2.5)}")

    //Определить два лямбда-выражения для преобразований
    val kgsToPoundsLambda = getConversionLambda("KgsToPounds")
    val poundsToUSTonsLambda = getConversionLambda("PoundsToUSTons")

    //Два лямбда-выражения преобразуются в одно новое
    val kgsToUSTonsLambda = combine(kgsToPoundsLambda, poundsToUSTonsLambda)

    //Использова новое лямбда-выражение для преобразования 17,4 кг в амер.тонны
    val value = 17.4
    println("$value kgs is ${convert(value, kgsToUSTonsLambda)} US tons")
}

typealias DoubleConversion = (Double) -> Double

fun convert(x: Double, converter: DoubleConversion): Double {
    val result = converter(x)
    println("$x is converted to $result")
    return result
}

fun getConversionLambda(str: String): DoubleConversion {
    if (str == "CentigradeToFahrenheit") {
        return { it * 1.8 + 32 }
    } else if (str == "KgsToPounds") {
        return { it * 2.204623 }
    } else if (str == "PoundsToUSTons") {
        return { it / 2000 }
    } else return { it }
}

fun combine(lambda1: DoubleConversion, lambda2: DoubleConversion): DoubleConversion {
    return { x: Double -> lambda2(lambda1(x)) }
}

//2fun convertFive(converter: (Int) -> Double): Double {
//2    val result = converter(5)
//2    println("5 is converted to $result")
//2    return result
//2}
