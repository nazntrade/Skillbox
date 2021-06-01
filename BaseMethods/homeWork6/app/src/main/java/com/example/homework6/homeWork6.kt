package com.example.homework6

fun main() {

    val man1 = Person(175, 80, "Bob")
    val man2 = Person(175, 80, "Bob")
    val man3 = Person(185, 90, "Leonardo")

    val peopleSet = mutableSetOf(man1, man2, man3)

    println(peopleSet.size)

    peopleSet.forEach { println(it) }

    man1.buyPet()
    man3.buyPet()
    man2.buyPet()
    man3.buyPet()
    man1.buyPet()
    man2.buyPet()
    println(man1)
    println(man2)
    println(man3)
    println(man1.pets)
    println(man2.pets)
    println(man3.pets)
}