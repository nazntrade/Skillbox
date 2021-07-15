package com.example.generics_01

import java.util.*

class Queue<T>(val linkedList: LinkedList<T>) {

    fun enqueue(item: T) {
        linkedList.addLast(item)
    }

    fun dequeue() {
        linkedList.pollFirst()
    }

    override fun toString(): String {
        return "Queue $linkedList"
    }
}