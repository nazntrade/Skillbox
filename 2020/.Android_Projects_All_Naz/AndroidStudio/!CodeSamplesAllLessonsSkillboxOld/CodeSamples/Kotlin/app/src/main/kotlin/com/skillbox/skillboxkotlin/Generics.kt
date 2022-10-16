package com.skillbox.skillboxkotlin


class GenericClass<out T>(default: T) {
    private var item: T = default

    fun getItem(): T {
        return item
    }
}

class ContrvariantClass<in T>(default: T) {
    private var item: T = default

    fun setItem(newValue: T) {
        item = newValue
    }
}

fun main() {
    val a = ContrvariantClass<Any>("324")
    updateGenerics(a)

    println(filterEvens(listOf(1, 2, 3)))
    println(filterEvens(listOf(1.0, 2.0, 3.5)))
    println(filterEvens(listOf(1f, 2.0f, 3f)))

    val result: Result<Any, String> = Success<Int, String>(342)

    val queue = Queue<Int>()
    queue.enquqe(324)
    queue.enquqe(32)

    queue.filter { it > 0 }

    val objectA: A = B()
    objectA.methodA()

}

fun updateGenerics(input: ContrvariantClass<Number>) {
    input.setItem(1.2)
}

fun sumGenerics(a: GenericClass<Number>, b: GenericClass<Number>): Double {
    return a.getItem().toDouble() + b.getItem().toDouble()
}

fun <T : Number> filterEvens(list: List<T>): List<T> {
    return list.filter { it.toInt() % 2 == 0 }
}

class Queue<T>(
    list: List<T> = emptyList()
) {

    private val mutableList: MutableList<T> = list.toMutableList()

    fun enquqe(item: T) {
        mutableList.add(item)
    }

    fun dequeue(): T? {
        return mutableList.takeIf { it.isNotEmpty() }?.let { it.removeAt(0) }
    }

    fun filter(predicate: (T) -> Boolean): Queue<T> {
        return Queue(mutableList.filter(predicate))
    }
}

sealed class Result<out T, R>
data class Success<out T, R>(
    val data: T
): Result<T, R>()

data class Error<T, R>(
    val error: R
): Result<T, R>()

open class A {
    open fun methodA() {
        println("print A")
    }
}

class B: A() {
    override fun methodA() {
        super.methodA()
        println("print B")
    }
}