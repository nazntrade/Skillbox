package com.example.homework_8_7_exceptions

class Wheel(var pressure: Double = 0.0) {

    class TooHighPressure : Exception()

    class TooLowPressure : Exception()

    class IncorrectPressure : Exception()

    fun setPressure(value: Double): Double {
        pressure = value
        if (pressure < 0 || pressure > 10) {
            try {
                throw IncorrectPressure()
            } catch (t: IncorrectPressure) {
                println("The pumping was failed. Not valid value. The pressure $pressure bar not in range 0..10.")
            }
        }
        return pressure
    }

    fun check() {

        if (pressure < 0 || pressure > 10) {
            try {
                throw IncorrectPressure()
            } catch (t: IncorrectPressure) {
                println("Check showed: not valid value. The pressure $pressure bar not in range 0..10.")
            }
        }

        if (pressure in 0.0..1.6) {
            try {
                throw TooLowPressure()
            } catch (t: TooLowPressure) {
                println("When the pressure was pumped to $pressure bar, the operation was successful. Use isn't possible. The pressure is too low.")
            }
        }
        if (pressure in 2.5..10.0) {
            try {
                throw TooHighPressure()
            } catch (t: TooHighPressure) {
                println("When the pressure was pumped to $pressure bar, the operation was successful. Use isn't possible. The pressure is too high.")
            }
        }
        if (pressure in 1.6..2.5) {
            println("When the pressure was pumped to $pressure bar, the operation was successful. Use is possible.")
        }
    }
}