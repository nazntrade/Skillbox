package com.skillbox.lesson6_1

fun main() {

    val shape = object : Shape {

        val additionalField = 123
        fun additionalMethod() = 234

        override val name: String = "Anonymous shape"

        override fun calculateArea(): Double = 0.0

    }

    shape.additionalField
    shape.additionalMethod()
    println(shape.additionalField)

}