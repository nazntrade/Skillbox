package com.example.generics_01

import java.util.*
import kotlin.random.Random

fun main() {

    val integers = genericFunction(list = listOf(1, 2488, 4, 67, 5, 34, 23, 66, 332, 6, 86))
    val realNumbers =
        genericFunction(list = listOf(33.4, 33, -45.3, 4, 58, 33.2, -54, 1, 0.4, 22.2))
    println(integers)
    println(realNumbers)

    val queue = Queue(linkedList = LinkedList(listOf("Jack", "Katherina", "Alex")))
    println(queue)
    queue.enqueue("Sarah")
    println(queue)
    queue.dequeue()
    println(queue)
    queue.dequeue()
    queue.dequeue()
    queue.dequeue()
    println(queue)

    val res1: Result<Number, String> = returnResult()
    val res2: Result<Any, String> = returnResult()
//  val res3: Result<Int, CharSequence> = returnResult()
//  val res4: Result<Int,Any> = returnResult()
}

fun <T : Number> genericFunction(list: List<T>): List<T> {
    return list.filter { it.toInt() % 2 == 0 }
}

private fun returnResult(): Result<Int, String> {
    val randomValue = Random.nextBoolean()
    return if (randomValue) {
        Result.Success(0)
    } else {
        Result.Error("Error")
    }
}