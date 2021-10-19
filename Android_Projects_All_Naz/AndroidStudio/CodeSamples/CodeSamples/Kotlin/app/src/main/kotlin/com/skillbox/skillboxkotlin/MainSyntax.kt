package com.skillbox.skillboxkotlin


fun main(args: Array<String>) {

    val shortNumber: Short = 324
    val integetNumber: Int = 5
    val longNumber: Long = 5L

    val floatNumber: Float = 5.32432422342342343243242f
    val doubleNumbber: Double = 2.2324324324874239847329

    val char: Char = 'V'
    val string: String = "Строка"
    val multilineString = """
        Многострочкая строка 1
        Вторая строка
    """

    val firstName = "Ivan"
    val lastName = "Petrov"
    val templateString = "His name is $firstName $lastName"

    val isMale = false
    val templateString2 = "${if (isMale) "His" else "Her"} name is $firstName $lastName"

    println(templateString2)

    val boolean: Boolean = true

    val andBoolean = boolean && false
    val orBoolean = false || boolean
    val negativeBoolean = !boolean
    val isNumbersEquals = 5 == 3
    val isFiveGreatThan3 = 5 > 3

    println(10 / 3)
    println(10 / 3.0)

    "1341".toInt()
    24.toLong()
    234.4f.toDouble()
    true.toString()
    324234.toString()

    val a = 100
    val b = 200

    if (a > 100) {
        print("1")
    } else if (b < 200) {
        print("2")
    } else if (a > 10) {
        print("3")
    } else if (b > 10) {
        print("4")
    }

//    val result = multiply(b = 5, a = 4)
//    println(result)

    val hasAccess = childHasAccess(180, 50, 12)
}

fun multiply(a: Int = 100, b: Int) = a * b

fun childHasAccess(height: Int, weight: Int, age: Int): Boolean {
    return height > 150 && weight > 40 && age > 13
}
