package com.skillbox.skillboxkotlin


fun main() {

    val nissan = Car.default
    nissan.openDoor {
        nissan.wheelCount
        print("adsasdas") }

    makeCalculations(
        {
            println("started")
        }, {
            println(it)
        })
}

inline fun makeCalculations(noinline startCallback: () -> Unit, resultCallback: (result: Int) -> Unit) {
    startCallback()
    val longOperationResult = 1 + 1
    resultCallback(longOperationResult)
}
