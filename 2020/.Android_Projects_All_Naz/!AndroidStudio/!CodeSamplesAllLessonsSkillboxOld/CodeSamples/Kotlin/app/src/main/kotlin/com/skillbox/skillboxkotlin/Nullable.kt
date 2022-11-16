package com.skillbox.skillboxkotlin


fun main() {

    print("Введите число: ")
    readLine()?.toIntOrNull()
        ?.let { number -> print("Вы ввели число: $number") }
        ?: println("Вы введи не число")

    functionWithNullableArg(null)

//    val nullableString: String? = null
//    val notNullableString: String = nullableString
}

fun functionWithNullableArg(arg: String?) {
    var a: Unit
    val b = println("Arg length is ${arg!!.length}")
}
