package com.skillbox.skillboxkotlin

fun main() {

    val car1 = Car(wheelCount = 4, maxSpeed = 200)
    val car2 = Car(wheelCount = 4, maxSpeed = 200)

    val car3 = car1
//    println("car1 equal to car2 by reference = ${car1 === car2}")
//    println("car1 equal to car3 by reference = ${car1 === car3}")
//
//    println("car1 equal to car2 by structure = ${car1 == car2}")
//    println("car1 equal to car3 by structure = ${car1 == car3}")

    val list = listOf(
        Car(wheelCount = 4, maxSpeed = 200),
        Car(wheelCount = 5, maxSpeed = 200),
        Car(wheelCount = 10, maxSpeed = 200)
    )

    val map: Map<Car, String> = hashMapOf(
        Car(wheelCount = 4, maxSpeed = 200) to "1",
        Car(wheelCount = 5, maxSpeed = 200) to "2"
    )

    print(map[Car(wheelCount = 4, maxSpeed = 200)])
}
