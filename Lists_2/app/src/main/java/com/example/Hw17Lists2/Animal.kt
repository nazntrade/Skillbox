package com.example.Hw17Lists2

sealed class Animal {
    data class Dog(
        val name: String,
        val breed: String,
        val avatarLink: String,
        val skill: String
    ) : Animal()

    data class Cat(
        val name: String,
        val breed: String,
        val avatarLink: String
    ) : Animal()

    data class Bird(
        val name: String,
        val breed: String,
        val avatarLink: String,
        val song: String,
        val alarm: String,
        val discountLink: String
    ) : Animal()
}
