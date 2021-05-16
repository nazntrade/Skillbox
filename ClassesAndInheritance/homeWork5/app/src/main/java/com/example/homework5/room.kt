package com.example.homework5

open class room(

    override val area: Double = 0.0,

    ) : Interface {

    open val title: String = "Ordinary room"

    fun getDescription() {
        println("room name: $title, room area: $area")
    }

    override fun toString(): String {
        return "room name: $title, room area: $area"
    }
}
