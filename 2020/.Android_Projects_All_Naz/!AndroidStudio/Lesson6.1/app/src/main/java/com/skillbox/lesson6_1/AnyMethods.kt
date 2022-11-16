
package com.skillbox.lesson6_1

fun main() {

    val car1 = Car(4,4,200)
    val car2 = Car(4,4,200)
    val car3 = car1

    println("car1 equals to car2 by reference = ${ car1 === car2}")
    println("car1 equals to car3 by reference = ${ car1 === car3}")
    println("car1 equals to car2 by value = ${ car1 == car2}")

    val cars = listOf(
        car1,
        Car(4, 2, 100),
        Car(3,4,180)
    )

    println(cars.contains(Car(4,2,100)))

    val map = hashMapOf(
        car1 to "1",
        car2 to "2",
        Car(4, 2, 100) to "3",
        )

    println(map[Car(4,4, 200)])

}