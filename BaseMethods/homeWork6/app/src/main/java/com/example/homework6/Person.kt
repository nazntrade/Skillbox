package com.example.homework6

class Person(
    val height: Int,
    val weight: Int,
    val name: String,
) {

    val pets = hashSetOf<Animal>()

    fun buyPet() {
        val animal = Animal(
            energy = (0..10).random(),
            weight = (30..100).random(),
            name = (('a'..'z')).random().toString()
        )
        pets.add(animal)

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (height != other.height) return false
        if (weight != other.weight) return false
        if (name != other.name) return false
        if (pets != other.pets) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + weight
        result = 31 * result + name.hashCode()
        result = 31 * result + pets.hashCode()
        return result
    }

    override fun toString(): String {
        return "height: $height, weight: $weight  name: $name, pets: $pets"
    }

}