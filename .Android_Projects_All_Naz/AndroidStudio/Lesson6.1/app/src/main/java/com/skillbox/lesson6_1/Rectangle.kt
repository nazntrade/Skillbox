package com.skillbox.lesson6_1

class Rectangle(
    x: Int,
    y: Int,
    width: Int,
    height: Int
) : AbstractShape(x, y), Comparable<Rectangle> {

    var width: Int by PrintAreaOnChengeDelegete(width)
    var height: Int by PrintAreaOnChengeDelegete(height)

    override val name: String = "Rectangle"

    override fun calculateArea(): Double = width * height.toDouble()

    override fun compareTo(other: Rectangle): Int {
        return (width + height) - (other.width + other.height)
    }

    override fun toString(): String {
        return "Rectangle(width=$width, height=$height)"
    }


}