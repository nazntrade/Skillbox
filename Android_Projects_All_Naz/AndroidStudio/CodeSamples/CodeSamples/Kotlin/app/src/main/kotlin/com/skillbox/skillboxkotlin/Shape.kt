package com.skillbox.skillboxkotlin

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface Shape {
    val name: String
    fun calculateArea(): Double

    class PrintAreaOnChangeProperty<T>(
        private var value: T
    ) : ReadWriteProperty<Shape, T> {

        override fun getValue(thisRef: Shape, property: KProperty<*>): T {
            return value
        }

        override fun setValue(thisRef: Shape, property: KProperty<*>, value: T) {
            println("${thisRef.name} area before setProperty ${property.name} = ${thisRef.calculateArea()}")
            this.value = value
            println("${thisRef.name} area after setProperty ${property.name} = ${thisRef.calculateArea()}")
        }
    }
}
