package com.skillbox.skillboxkotlin


fun main() {

    val pair1 = Pair("Name", "Phone")
    val pair2 = "Name" to "Phone"

//    println(pair1.first)
//    println(pair1.second)

    val simpleMap = mapOf(
        "Name1" to "Phone1",
        "Name1" to "Phone12",
        "Name2" to "Phone2",
        "Name3" to "Phone3"
    )

    println(simpleMap["Name1"])

    val mutableMap1 = simpleMap.toMutableMap()
    val mutableMap2 = mutableMapOf<String, String>()
    mutableMap1["New key"] = "New value"
    mutableMap1.put("New key", "New value")
    mutableMap1.remove("Name1")

    mutableMap2.toMap()
    mutableSetOf<String>().toSet()
    mutableListOf<String>().toList()

//    sortedMapOf()
//    hashMapOf<String, String>().toMap()

    println(simpleMap.keys)
    println(simpleMap.values.groupBy { })

    for (key in simpleMap.keys) {
        println("$key - ${simpleMap[key]}")
    }

    simpleMap.forEach { entry -> println("${entry.key}- ${entry.value}") }

    simpleMap.forEach { (key, value) -> println("$key - $value") }
}
