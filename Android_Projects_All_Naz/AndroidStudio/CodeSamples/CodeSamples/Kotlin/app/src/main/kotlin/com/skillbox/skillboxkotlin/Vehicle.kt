package com.skillbox.skillboxkotlin

open class Vehicle(
    val maxSpeed: Int
) {

    var currentSpeed: Int = 0
        private set

    var currentFuel = 0
        private set

    open fun accelerate(speed: Int) {
        val maxDelta = minOf(maxSpeed - currentSpeed, speed)
        val needFuel = maxDelta / 2
        if (currentFuel >= needFuel) {
            currentSpeed += maxDelta
            useFuel(needFuel)
        } else {
            println("car has no fuel")
        }
    }

    open fun getTitle() = "Vehicle"

    fun decelerate(speed: Int) {
        val maxDelta = minOf(currentSpeed, speed)
        currentSpeed -= maxDelta
    }

    private fun useFuel(fuelCount: Int) {
        currentFuel -= fuelCount
    }

    fun refuel(fuelCount: Int) {
        currentFuel.takeIf { currentSpeed == 0 }
            ?.let {
                currentFuel += fuelCount
            } ?: println("you cant refuel on the run")
    }
}
