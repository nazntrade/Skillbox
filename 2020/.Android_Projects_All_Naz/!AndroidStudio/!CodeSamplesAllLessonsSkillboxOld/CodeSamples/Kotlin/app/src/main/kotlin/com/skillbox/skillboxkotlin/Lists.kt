package com.skillbox.skillboxkotlin


fun main() {

    val stringList = listOf("1", "2")
    val carList: List<Car> = listOf()

    val list = listOf(1, 2, 3, 4)
    val mutableList = mutableListOf(1, 2, 3, 4)
    mutableList[1] = 200

    println(list)
    val secondElement = list[1]
    val emptyList = emptyList<String>()

    mutableList.removeAt(0)
    mutableList.add(324)
    mutableList.add(index = 2, element = 3444)
    mutableList.addAll(list)

    genericFunction<String>("")
}

fun <T> genericFunction(input: T): T {
    return input
}
