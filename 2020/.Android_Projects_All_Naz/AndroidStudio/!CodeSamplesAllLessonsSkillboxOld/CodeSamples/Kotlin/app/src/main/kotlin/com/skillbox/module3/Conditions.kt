package com.skillbox.module3

fun main() {
    println(getDeveloperPosition(-1))
    println(getDeveloperPosition(0))
    println(getDeveloperPosition(1))
    println(getDeveloperPosition(2))
    println(getDeveloperPosition(3))
    println(getDeveloperPosition(10))
}

fun maxInt(a: Int, b: Int): Int = if (a < b) b else a

fun calculatePrice(booleanParam: Boolean): Int {
    return if(booleanParam) {
        val intermediateResult = 234 + 432
        intermediateResult + 3
    } else {
        val intermediateResult = 14 + 3
        intermediateResult + 1
    }
}

fun getCarType(maxSpeed: Int, hasSportMode: Boolean): String {
    return when {
        maxSpeed < 20 -> "Трактор"
        maxSpeed < 60 -> "Медленная машина"
        hasSportMode && maxSpeed < 200 -> "Обычная машина sad asdasdas das dsa das d as d as das d as d"
        else -> ""
    }
}

fun getDeveloperPosition(experience: Int): String {
    return if(experience < 0) {
        "Incorrect experience"
    } else {
        when(experience) {
            0 -> "Intern"
            in 1..2 -> "Junior"
            in 3..4 -> "Middle"
            else -> "Senior"
        }
    }
}