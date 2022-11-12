package com.skillbox.lesson5_classes

import kotlin.math.max

 class Car constructor(
        val wheelCount: Int,
        val doorCount: Int,
        val bodyLength: Int,
        val bodyWight: Int,
        val bodyHeight: Int
) {

    var currentSpeed: Int = 0
        get() {
           println("currentSpeed get")
           return field
        }
        private set(value) {
            println("currentSpeed set $value")
            field = value
        }

    var fuelCount: Int = 0
        private set

    fun accelerate(speed: Int) {

        val needFuel = speed / 2

        if (fuelCount >= needFuel) {
            currentSpeed += speed
            useFuel(needFuel)
        } else {
            println("Car has no fuel")
        }
    }

    fun decelerate(speed: Int) {
        currentSpeed = maxOf(0, currentSpeed - speed)
    }

    private fun useFuel(fuelCount: Int) {
        this.fuelCount -= fuelCount
    }

    fun reFuel(fuelCount: Int) {
        if (currentSpeed == 0) {
            this.fuelCount += fuelCount
        } else {
            println("you can't reFuel because currentSpeed !=0")
        }
    }

}