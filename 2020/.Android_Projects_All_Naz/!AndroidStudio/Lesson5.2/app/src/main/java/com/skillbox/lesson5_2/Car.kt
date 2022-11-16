package com.skillbox.lesson5_2

class Car(
        val wheelCount: Int,
        val doorCount: Int,
        maxSpeed: Int
) : Vehicle(maxSpeed) {

    var isDoorOpen: Boolean = false
        private set

    fun openDoor() {
        if (!isDoorOpen) {
            println("door is opened")
        }
        isDoorOpen = true
    }

    fun closeDoor() {
        if (isDoorOpen) {
            println("door is closed")
        }
        isDoorOpen = false
    }

    override fun getTitle(): String = "Car"

    override fun accelerate(speed: Int) {
        if (isDoorOpen) {
            println("you can't accelerate with opened door")
        } else {
            super.accelerate(speed)
        }
    }

    fun accelerate(speed: Int, force: Boolean) {
        if (force) {
            if (isDoorOpen) {
                println("warning, accelerate with opened door")
                super.accelerate(speed)
            }
        } else accelerate(speed)
    }
}