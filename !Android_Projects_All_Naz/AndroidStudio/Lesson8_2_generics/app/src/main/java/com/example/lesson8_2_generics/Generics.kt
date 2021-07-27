package com.example.lesson8_2_generics

fun main() {
    val genericObject = GenericClass(0.0)

    val genericObject2 = GenericClass(0.0)
    val genericObject3 = GenericClass<Float>(0.0F)
    val genericObject4 = GenericClass<Float>(0f)

    println(sumGeneric(genericObject, genericObject2))
    println(sumGeneric(genericObject3, genericObject4))

    updateContravariant(ContravariantClass<Any>(234))

}

class GenericClass<out T : Number, in R>(defaultValue: T) {
    private var item: T = defaultValue

    fun getItem(): T {
        return item
    }

    fun setItem(newR: R) {

    }
}

class ContravariantClass<in T>(defaultValue: T) {
    private var item: T = defaultValue

    fun setItem(newItem: T) {
        item = newItem
    }
}

fun updateContravariant(input: ContravariantClass<Number>) {
    input.setItem(2.2)

}

fun sumGeneric(a: GenericClass<Number>, b: GenericClass<Number>): Int {
    return a.getItem().toInt() + b.getItem().toInt()
}

fun <T> printGenericObject(item: T) {
    println(item.toString())
}