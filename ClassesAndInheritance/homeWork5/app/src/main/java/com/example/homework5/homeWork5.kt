package com.example.homework5

fun main() {

    val ordinaryRoom = room(2.5)
    ordinaryRoom.getDescription()

    val bedroom = bedroom()
    bedroom.getDescription()

    val childrenRoom = childrensRoom()
    childrenRoom.getDescription()

    val freeRoom = freeRoom("Gum")
    freeRoom.getDescription()

    val livingRoom = livingRoom()
    livingRoom.getDescription()

    val kitchen = kitchen()
    kitchen.getDescription()

    val bathroom = bathroom()
    bathroom.getDescription()

    val workRoom = workRoom()
    workRoom.getDescription()
    workRoom.pc()

}