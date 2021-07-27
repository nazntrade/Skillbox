package com.skillbox.lesson6_1

class Circle(
    radius: Int
) : Shape {

    override val name: String = "Circle"

    override fun calculateArea(): Double {
    return Math.PI * radius * radius
    }

    var radius: Int by PrintAreaOnChengeDelegete(radius)

}