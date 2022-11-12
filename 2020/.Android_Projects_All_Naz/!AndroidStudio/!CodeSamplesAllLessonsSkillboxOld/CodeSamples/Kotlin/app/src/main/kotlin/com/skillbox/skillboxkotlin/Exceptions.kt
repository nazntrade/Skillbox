package com.skillbox.skillboxkotlin

import java.lang.IllegalStateException
import java.lang.NullPointerException


fun main() {
    functionWithException()
}

fun functionWithException() {
    println("start")
    try {
        println("before")
        throw IllegalStateException("some info about exception")
        println("after")
    } catch (e: NullPointerException) {
        println("catch")
    } finally {
        println("finally")
    }
    println("end")
}

fun function1() {
    println("function1 before")
    functionWithExceptionHandling()
    println("function1 after")
}

fun function2() {
    println("function2 before")
    functionWithException()
    println("function2 after")
}

fun functionWithExceptionHandling() {
    try {
        println("functionWithExceptionHandling before")
        function2()
        println("functionWithExceptionHandling after")
//    } catch (t: Throwable) {
//        println("catch Throwable with message = ${t.message}")
    } finally {
        println("finally block")
    }
}
