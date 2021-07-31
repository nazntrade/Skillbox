package com.example.homework_8_7_exceptions

fun main() {

    val wheel = Wheel(0.0)
    wheel.setPressure(-1.0)
    wheel.check()
    wheel.setPressure(1.0)
    wheel.check()
    wheel.setPressure(2.0)
    wheel.check()
    wheel.setPressure(5.0)
    wheel.check()
    wheel.setPressure(15.0)
    wheel.check()

//    val wheel2 = Wheel(0.0)
//    wheel2.setPressure(0.9)
//    wheel2.check()
//
//    val wheel3 = Wheel(0.0)
//    wheel3.setPressure(8.5)
//    wheel3.check()
//
//    val wheel4 = Wheel(0.0)
//    wheel4.setPressure(2.1)
//    wheel4.check()
}

