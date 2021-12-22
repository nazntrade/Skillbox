package com.skillbox.skillboxkotlin

class AirPlane(maxSpeed: Int) : Vehicle(maxSpeed) {

    val anonymShape = object : Shape {

        val additionalProperty = 342

        override val name: String = "Anonymous"

        override fun calculateArea(): Double =
            0.0

        fun additionalMethod() {
            println("print from additional method")
        }
    }

    override fun getTitle(): String = "AirPlane"
}
