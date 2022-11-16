package com.skillbox.skillboxkotlin


fun main() {
    Cars.nissan
    Cars.toyota
    Cars.customMethod()

    Cars.refuel(100)

    Car.createWithDefaultWheels(200)
    Car.default

    val companionObject = Car
    val singletonObject = Cars

    Car.Companion.default

}

object Cars : Vehicle(1000) {

    val toyota = Car(wheelCount = 4, maxSpeed = 200)
    val nissan = Car(wheelCount = 4, maxSpeed = 250)

    fun customMethod() = "value"
}
