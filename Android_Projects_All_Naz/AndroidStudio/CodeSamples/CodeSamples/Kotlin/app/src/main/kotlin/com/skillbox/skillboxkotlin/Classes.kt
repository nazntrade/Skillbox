package com.skillbox.skillboxkotlin

fun main() {

//    val vehicles = listOf(
//        Car(wheelCount = 4, maxSpeed = 200),
//        Vehicle(maxSpeed = 220),
//        Car(wheelCount = 4, maxSpeed = 300),
//        AirPlane(maxSpeed = 900),
//        Vehicle(maxSpeed = 220)
//    )
//
//    vehicles.forEachIndexed { index, element ->
//        println("#$index = ${element.getTitle()}")
//    }
//

//    val vehicle: Vehicle = Vehicle(100)
//    println("vehicle is car = ${vehicle is Car}")
//    val carFromVehicle = vehicle as? Car
//    carFromVehicle?.openDoor()
//    carFromVehicle?.closeDoor()
//
    val nissan = Car(wheelCount = 4, maxSpeed = 250)
//    println("nissan is vehicle = ${nissan is Vehicle}")
//
//
    with(nissan) {
        refuel(60)
        openDoor()
        closeDoor()
        accelerate(100)
//        println("nissan currentSpeed = $currentSpeed")
//        decelerate(100)
    }
}

class TestClass {
    fun methodA() {}
    private fun methodB() {}
    public fun methodC() {}
    protected fun methodD() {}
}

val a = TestClass()
