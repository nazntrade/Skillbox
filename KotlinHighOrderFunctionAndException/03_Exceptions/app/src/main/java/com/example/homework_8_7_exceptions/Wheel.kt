package com.example.homework_8_7_exceptions

fun main() {

    val wheel = Wheel(0.0)
    wheel.setPressure(15.4)

}

class Wheel(val pressure: Double = 0.0) {

    class TooHighPressure(s: String) : Exception() {}

    class TooLowPressure(s: String) : Exception() {}

    class IncorrectPressure(s: String) : Exception() {}

    fun setPressure(value: Double) {
        val pressure = value + pressure
        if (pressure < 0 || pressure > 10) {
            throw IncorrectPressure("The pressure not in range 0..10: $pressure")
        }
    }

    fun check(pressure: Double) {
        if (pressure < 0 || pressure > 10) {
            throw IncorrectPressure("The pressure not in range 0..10: $pressure")
        }
        if (pressure < 1.6) {
            throw TooLowPressure("The pressure is too low: $pressure - increase pressure")
        }
        if (pressure > 2.5) {
            throw TooHighPressure("The pressure is too high: $pressure - turn down the pressure")
        }
    }
}