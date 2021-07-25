package com.example.homework_8_5

import java.util.*

class Queue<T>(val linkedList: LinkedList<T>) {

    fun enqueue(item: T) {
        linkedList.addLast(item)
    }

    fun filterElem() {
        val newQueue = linkedList.filter { it.toString().length > 4 }
        println("newQueue: $newQueue")
    }

    fun dequeue() {
        linkedList.pollFirst()
    }

    override fun toString(): String {
        return "Queue $linkedList"
    }
}