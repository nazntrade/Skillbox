package com.skillbox.skillboxkotlin

fun main() {
    println(functionReturnsAnyType(2))
    println(functionWithAnyParameter(2))
    println(functionWithAnyParameter("string"))
    println(functionWithAnyParameter(true))
}

fun functionReturnsAnyType(int: Int): Any {
    return if (int < 2) {
        "string"
    } else {
        3
    }
}

fun functionWithAnyParameter(any: Any): Int = 5

fun functionReturnsUnitExplicitly() {
    return
}

fun functionReturnsUnitImplicitly() {}
