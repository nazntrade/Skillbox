package com.skillbox.skillboxkotlin

fun main() {

    val set = setOf(1, 2, 3, 4, 5, 1, 2, 6)
    println(set)

    val setUnion = listOf(1, 2, 3, 2).union(setOf(2, 3))
    println(setUnion)

    val setSubstraction = setOf(1, 2, 3).subtract(setOf(1, 2, 4))
    println(setSubstraction)

    val setIntersection = setOf(1, 2).intersect(setOf(2, 3))
    println(setIntersection)

    val naturalOrderSet = sortedSetOf(2, 5, 7, 1, 3, 4)
    println(naturalOrderSet)

    println(naturalOrderSet.descendingSet())

    val mutableSet = mutableSetOf(1, 2, 2, 2, 12, 31, 2)
    mutableSet.add(100)

    setOf(123, 231, 3424).toMutableSet()

    val hashset = hashSetOf(1, 2, 3, 5, 6, 7)
    hashset.add(100)
    hashset.remove(2)
    println(hashset)

    println(hashset.contains(1))
}
