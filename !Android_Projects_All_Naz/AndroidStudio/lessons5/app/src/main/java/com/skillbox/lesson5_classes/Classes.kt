package com.skillbox.lesson5_classes

fun main() {
    val tesla = Car(
        wheelCount = 4, doorCount = 2, bodyLength = 500, bodyWight = 200, bodyHeight = 150
    )

    println("Tesla wheelCount = ${tesla.wheelCount}, doorCount = ${tesla.doorCount}, bodyLength = ${tesla.bodyLength}, currentSpeed = ${tesla.currentSpeed}")

    tesla.accelerate(100)

    println("Tesla currentSpeed = ${tesla.currentSpeed}")

    tesla.decelerate(50)

    println("Tesla currentSpeed = ${tesla.currentSpeed}")
    println("tesla fuelCount = ${tesla.fuelCount}")


}