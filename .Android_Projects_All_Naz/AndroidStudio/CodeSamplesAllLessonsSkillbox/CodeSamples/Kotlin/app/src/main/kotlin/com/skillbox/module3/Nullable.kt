package com.skillbox.module3

fun main() {

//    readLine()?.toIntOrNull()
//        ?.let {
//            println("Вы ввели число $it")
//        }
//        ?: println("Вы ввели не число")

    printLength(null)

}

fun printLength(string: String?) {
    print("Длина строки = ${string?.length}")
}