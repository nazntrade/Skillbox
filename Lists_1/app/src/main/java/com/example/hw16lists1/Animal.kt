package com.example.hw16lists1

sealed class Animal {
    data class Dog(
        val name: String,
        val breed: String,
        val avatarLink: String,
        val dogSize: Int,
        ) : Animal()

    data class Cat(
        val name: String,
        val breed: String,
        val avatarLink: String,
        val toiletTrained: String,
        ) : Animal()

    data class Bird(
        val name: String,
        val breed: String,
        val avatarLink: String,
        val song: String,
        val funAlarm: String,
        val discountLink: String
    ) : Animal()
}
