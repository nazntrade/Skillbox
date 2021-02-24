package com.skillbox.myapplication

fun main() {
    val firstName: String
    firstName = "Andrew"
    val lastNameString = "Nazentsev"

    var heightInt = 175
    var weight = 30f

    val isChild: Boolean = (heightInt < 140) || (weight < 40)

    val info = """
        Firstname: ${firstName} 
        Lastname: ${lastNameString} 
        Height: ${heightInt} 
        Weight: ${weight} 
        Child: ${isChild}
        """

print(info)
}