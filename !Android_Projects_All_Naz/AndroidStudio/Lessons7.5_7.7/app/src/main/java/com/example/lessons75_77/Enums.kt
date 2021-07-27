package com.example.lessons75_77

fun main() {
    val color = Color.BLACK.hex
    Color.RED.draw()

    Color.values().forEach {
        println(it.ordinal)
    }

    Color.fromName("4525")

    SealedColor.Black
    SealedColor.CustomColor("#ff0")

}

fun invertColor(color: SealedColor): SealedColor {
    return when (color) {
        SealedColor.Black -> SealedColor.White
        SealedColor.White -> SealedColor.Black
        is SealedColor.CustomColor -> {
            SealedColor.White
        }
    }
}