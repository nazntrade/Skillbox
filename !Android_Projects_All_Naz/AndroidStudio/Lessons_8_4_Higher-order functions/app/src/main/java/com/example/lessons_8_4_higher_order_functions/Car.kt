package com.example.lessons_8_4_higher_order_functions
import java.sql.Driver

class Car(
    val wheelCount: Int,
    val doorCount: Int,
    maxSpeed: Int,
    val callback: () -> Unit
) : Vehicle(maxSpeed) {

    var isDoorOpen: Boolean = false
        private set

    private lateinit var driver: User

    private val engine by lazy {

        Engine()

    }

    operator fun component1(): Int = wheelCount
    operator fun component2(): Int = doorCount


    //                changed                       lambda
    fun openDoor(openCallBack: () -> Unit = { println("Open door") }) {
        if (!isDoorOpen) {
            openCallBack()
        }
        isDoorOpen = true
    }

    //                changed                       lambda
    fun closeDoor(closeCallBack: () -> Unit = { println("door is closed") }) {
        if (isDoorOpen) {
            closeCallBack()
        }
        isDoorOpen = false
    }

    override fun getTitle(): String = "Car"

    override fun accelerate(speed: Int) {

        engine.use()

        if (isDoorOpen) {
            println("you can't accelerate with opened door")
        } else {
            super.accelerate(speed)
        }
    }

    fun setDriver(driver: User) {
        this.driver = driver
    }

    fun accelerate(speed: Int, force: Boolean) {
        if (force) {
            if (isDoorOpen) {
                println("warning, accelerate with opened door")
                super.accelerate(speed)
            }
        } else accelerate(speed)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (wheelCount != other.wheelCount) return false
        if (doorCount != other.doorCount) return false
        if (isDoorOpen != other.isDoorOpen) return false

        return true
    }

    override fun hashCode(): Int {
        var result = wheelCount
        result = 31 * result + doorCount
        result = 31 * result + isDoorOpen.hashCode()
        return result
    }

    companion object {
        val default = Car(4, 4, 200)

        fun createWithDefaultWheelCount(doorCount: Int, maxSpeed: Int): Car {
            return Car(4, doorCount, maxSpeed)
        }
    }

}