package com.skillbox.skillboxkotlin

class Rectangle(
    width: Int,
    height: Int,
    x: Int,
    y: Int
) : AbstractShape(x, y), Comparable<Rectangle> {

    var width: Int by Shape.PrintAreaOnChangeProperty(width)
    var height: Int by Shape.PrintAreaOnChangeProperty(height)

    override val name: String = "Rectangle"

    override fun calculateArea(): Double = width * height.toDouble()

    override fun compareTo(other: Rectangle): Int {
        return (width + height) - (other.width + other.height)
    }

    override fun toString(): String {
        return "Rectangle(width=$width, height=$height, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rectangle

        if (width != other.width) return false
        if (height != other.height) return false
        if (name != other.name) return false

        return true
    }

    operator fun plus(other: Rectangle): Rectangle {
        return Rectangle(width + other.width, height + other.height, 0, 0)
    }

    operator fun unaryMinus(): Rectangle {
        return Rectangle(-width, -height, 0, 0)
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + name.hashCode()
        return result
    }
}
