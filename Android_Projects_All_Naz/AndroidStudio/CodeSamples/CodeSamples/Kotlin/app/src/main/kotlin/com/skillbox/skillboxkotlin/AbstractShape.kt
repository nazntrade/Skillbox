package com.skillbox.skillboxkotlin

abstract class AbstractShape(
    private var x: Int,
    private var y: Int
) : Shape {

    fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
        println("shape is moved to ($x, $y)")
    }

    fun printPosition() {
        println("shape center ($x, $y)")
    }
}
