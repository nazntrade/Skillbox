package com.skillbox.lesson5_2

class Rectangle(x: Int,
                y: Int,
                private val width: Int,
                private val height: Int
) : AbstractShape(x, y), Comparable<Rectangle> {

    override val name: String = "Rectangle"

    override fun calculateArea(): Double = width * height.toDouble()

    override fun compareTo(other: Rectangle): Int {
        return (width + height) - (other.width + other.height)
    }

    override fun toString(): String {
        return "Rectangle(width=$width, height=$height)"
    }


}