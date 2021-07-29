package com.example.lessons_8_4_higher_order_functions

fun main() {
//    val lambda = { println("Text from lambda") }
//    lambda()
//    val lambdaWithParams = { x: Int -> println("X from lambda: $x") }
//    lambdaWithParams(10)
//    val sumLambda = { x: Int, y: Int -> x + y }
//    println("Sum x and y = ${sumLambda(30, 1000)}")

    val users = listOf(
        User("Vasya", "Sergeev", 30),
        User("Vasya", "Ivanov", 20),
        User("Vasya", "Petrov", 10),
        User("Vasya", "Sidorov", 50)
    )

    val user1 = users[0]
    val oldestUser = users.maxByOrNull { user -> user.age.let { it + 1 } }
//    val maxNameUser = users.maxByOrNull { it.getFullnameLength() }
    val maxNameUser = users.maxByOrNull(User::getFullnameLength)
    println(maxNameUser)
//  val oldestUser = users.maxByOrNull({ user: User -> user.age}) // in lesson .maxBy
//  val oldestUser = users.maxByOrNull({ user -> user.age}) // is the same

    val car = Car.default
    car.openDoor()
    car.closeDoor { println("close door custom") }



    makeCalculations({
        println("result = $it")
    }, { })
// the same - makeCalculations({ println("result = $it") }, { })

}

inline fun makeCalculations(callBack: (Int) -> Unit, noinline callBack2: (Int) -> Unit) {
    val value = 1 + 1
    callBack(value)
}