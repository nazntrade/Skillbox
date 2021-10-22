package com.skillbox.skillboxkotlin

interface Source<in T> {
    fun get(a: T)
}

fun main() {
}

fun test(input: Source<Any>) {
    val s: Source<String> = input
}
