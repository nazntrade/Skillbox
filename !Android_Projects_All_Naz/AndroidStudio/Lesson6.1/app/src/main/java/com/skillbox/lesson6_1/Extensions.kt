package com.skillbox.lesson6_1

fun main() {

    1.toBoolean()
    1.isPositive

    val nullableInt: Int? = null
    nullableInt.orDefault(0)

}

private fun Int.toBoolean(): Boolean {
    return this != 0
}

val Int.isPositive: Boolean
    get() = this > 0

private fun Int?.orDefault(defaultValue: Int): Int {
    return this ?: defaultValue
}
