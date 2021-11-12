package com.skillbox.skillboxkotlin

class Circle(
    raduis: Int
) : Shape {

    var radius: Int by Shape.PrintAreaOnChangeProperty(raduis)

    override val name: String = "Circle"

    override fun calculateArea(): Double {
        return Math.PI * radius * radius
    }
}
