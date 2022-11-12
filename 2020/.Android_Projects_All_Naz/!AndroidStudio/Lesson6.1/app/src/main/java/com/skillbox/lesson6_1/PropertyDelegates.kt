package com.skillbox.lesson6_1

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {

    val car = Car(4,4,200)

    car.openDoor()
    car.closeDoor()
    car.accelerate(20)

}

class PrintAreaOnChengeDelegete<V>(
    private var value: V
) : ReadWriteProperty<Shape, V> {
    override fun getValue(thisRef: Shape, property: KProperty<*>): V = value

    override fun setValue(thisRef: Shape, property: KProperty<*>, value: V) {
        println("before change property ${property.name} = ${thisRef.calculateArea()}")
        this.value = value
        println("after change property ${property.name} = ${thisRef.calculateArea()}")

    }
}