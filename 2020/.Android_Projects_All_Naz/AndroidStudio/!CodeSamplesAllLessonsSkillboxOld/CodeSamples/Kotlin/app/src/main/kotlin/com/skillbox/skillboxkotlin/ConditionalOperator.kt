package com.skillbox.skillboxkotlin


fun main() {
    println(getDeveloperPosition(-100))
    println(getDeveloperPosition(0))
    println(getDeveloperPosition(1))
    println(getDeveloperPosition(2))
    println(getDeveloperPosition(3))
    println(getDeveloperPosition(4))
    println(getDeveloperPosition(5))
}

fun findMaxInt(a: Int, b: Int): Int = if (a < b) b else a

fun getCarClass(maxSpeed: Int, hasSportMode: Boolean): String {
    return when {
        maxSpeed < 20 -> "Трактор"
        maxSpeed < 60 -> "Медленная машина"
        maxSpeed < 120 -> "Обычная машина"
        hasSportMode || maxSpeed < 200 -> "Быстрая машина"
        else -> "Сверхбыстрая машина"
    }
}

fun getDeveloperPosition(experience: Int): String {
    return if (experience < 0) {
        "incorrect experience"
    } else {
        when (experience) {
            0 -> "intern"
            in 1..2 -> "junior"
            3, 4 -> "middle"
            else -> "senior"
        }
    }
}

fun calculatePrice(booleanParam: Boolean): Int {
    return if (booleanParam) {
        val intermediateResult = 1 + 2
        intermediateResult + 2
        intermediateResult + 5
    } else {
        val intermediateResult = 2 + 4
        intermediateResult + 100
    }
}
