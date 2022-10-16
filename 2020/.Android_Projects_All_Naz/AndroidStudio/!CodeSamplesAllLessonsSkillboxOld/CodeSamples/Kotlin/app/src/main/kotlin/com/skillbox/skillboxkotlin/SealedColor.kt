package com.skillbox.skillboxkotlin

sealed class SealedColor(val hex: String) {
    object White : SealedColor("#fff")
    object Black : SealedColor("#000")
    object Red : SealedColor("#f00")
    object Blue : SealedColor("#00f")

    class CustomColor(hex: String) : SealedColor(hex)
}
