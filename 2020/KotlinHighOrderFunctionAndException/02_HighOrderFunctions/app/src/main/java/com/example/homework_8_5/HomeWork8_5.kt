package com.example.homework_8_5

import java.util.*

fun main() {

    val queue = Queue(linkedList = LinkedList(listOf("Jack", "Katherina", "Alex", "Sonya")))
    println(queue)
    queue.enqueue("Sarah")
    println(queue)
    queue.filterElem()
    
}

