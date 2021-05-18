package com.example.homework5

fun main() {

    val ordinaryRoom = Room(2.5)
    ordinaryRoom.getDescription()

    val bedroom = Bedroom()
    bedroom.getDescription()

    val childrenRoom = ChildrensRoom()
    childrenRoom.getDescription()

    val freeRoom = FreeRoom("Gum")
    freeRoom.getDescription()

    val livingRoom = LivingRoom()
    livingRoom.getDescription()

    val kitchen = Kitchen()
    kitchen.getDescription()

    val bathroom = Bathroom()
    bathroom.getDescription()

    val workRoom = WorkRoom()
    workRoom.getDescription()
    workRoom.pc()

}