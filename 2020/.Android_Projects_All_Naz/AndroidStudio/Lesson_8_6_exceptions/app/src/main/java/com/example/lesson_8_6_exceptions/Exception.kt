package com.example.lesson_8_6_exceptions

import java.lang.NullPointerException

fun main() {

    functionOuter()

}

fun functionOuter() {
    println("functionOuter before")
    functionWithExceptionHandling()
    println("functionOuter after")
}


fun functionWithExceptionHandling() {
    println("functionWithExceptionHandling before")
    try {
        functionInner()
    } catch (t: Throwable) {
        println("catch Throwable with info = ${t.message}")
    }
    println("functionWithExceptionHandling after")
}


fun functionInner() {
    println("functionInner before")
    functionWithException()
    println("functionInner after")

}


fun functionWithException() {
    println("functionWithException before")
    throw Exception("exception with additional info")
    println("functionWithException after")

}
