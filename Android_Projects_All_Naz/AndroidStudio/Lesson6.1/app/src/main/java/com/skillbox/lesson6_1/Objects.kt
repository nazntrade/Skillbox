package com.skillbox.lesson6_1

fun main() {

    Cars.nissan
    Cars.someMethod()
    Cars.accelerate(20)

    Car.default
    Car.createWithDefaultWheelCount(4, 200)

    val a = Car
    val b = Cars

}

private object Cars : Vehicle(100) {
    val toyota = Car(4, 4, 200)
    val nissan = Car(4, 4, 200)

    fun someMethod() {
    }

}