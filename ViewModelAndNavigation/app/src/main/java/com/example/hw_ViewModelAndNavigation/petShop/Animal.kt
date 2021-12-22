package com.example.hw_ViewModelAndNavigation.petShop

sealed class Animal {
    data class Dog(
        val id: Long,
        val name: String,
        val breed: String,
        val avatarLink: String,
        val skill: String
    ) : Animal()

    data class Cat(
        val id: Long,
        val name: String,
        val breed: String,
        val avatarLink: String
    ) : Animal()

    data class Bird(
        val id: Long,
        val name: String,
        val breed: String,
        val avatarLink: String,
        val song: String,
        val alarm: String,
        val discountLink: String
    ) : Animal()
}
