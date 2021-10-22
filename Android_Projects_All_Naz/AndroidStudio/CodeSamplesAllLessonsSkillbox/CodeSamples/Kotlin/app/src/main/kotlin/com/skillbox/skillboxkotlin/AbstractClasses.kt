package com.skillbox.skillboxkotlin

fun main() {
    val shape: Shape = Rectangle(width = 4, height = 5, x = 4, y = 5)
//    with(shape) {
//        printPosition()
//        moveTo(1, 1)
//        printPosition()
//    }

    println("shape area = ${shape.calculateArea()}")
    println("shape name = ${shape.name}")

    val smallRectangle = Rectangle(width = 2, height = 3, x = 4, y = 5)
    val largeRectangle = Rectangle(width = 20, height = 30, x = 4, y = 5)

    println("smallRectangle < largeRectangle = ${smallRectangle < largeRectangle}")

    val set: MutableSet<Rectangle> = linkedSetOf()
    set.add(largeRectangle)
    set.add(smallRectangle)

    println(set)

    val anonymShape = object : Shape {

        val additionalProperty = 342

        override val name: String = "Anonymous"

        override fun calculateArea(): Double = 0.0

        fun additionalMethod() {
            println("print from additional method")
        }
    }

    anonymShape.additionalProperty
    anonymShape.additionalMethod()
    anonymShape.calculateArea()
}
