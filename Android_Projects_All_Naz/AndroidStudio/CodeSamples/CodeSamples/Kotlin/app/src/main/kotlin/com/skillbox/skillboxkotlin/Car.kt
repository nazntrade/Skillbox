package com.skillbox.skillboxkotlin

class Car(
    val wheelCount: Int,
    maxSpeed: Int
) : Vehicle(maxSpeed) {

    private var isDoorOpen = false

    private lateinit var driver: User

    operator fun component1() = wheelCount
    operator fun component2() = maxSpeed

    private val engine: Engine by lazy {
        Engine()
    }

    fun openDoor(onOpened: () -> Unit = { println("open door") }) {

        if (!isDoorOpen) {
            onOpened()
        }
        isDoorOpen = true
    }

    fun closeDoor(onClosed: () -> Unit = { println("close door") }) {
        if (isDoorOpen) {
            onClosed()
        }
        isDoorOpen = false
    }

    fun accelerate(speed: Int, force: Boolean) {
        if (force) {
            if (isDoorOpen) {
                println("warning - increasing speed with opened door")
            }
            super.accelerate(speed)
        } else {
            accelerate(speed)
        }
    }

    fun setDriver(driver: User) {
        this.driver = driver
    }

    override fun accelerate(speed: Int) {
        engine.use()
        if (isDoorOpen) {
            println("you cant increase speed with opened door")
        } else {
            super.accelerate(speed)
        }
    }

    override fun getTitle(): String = "Car"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (wheelCount != other.wheelCount) return false
        if (isDoorOpen != other.isDoorOpen) return false

        return true
    }

    override fun hashCode(): Int {
        var result = wheelCount
        result = 31 * result + isDoorOpen.hashCode()
        println("hashcode returns $result")
        return result
    }

    companion object Companion {
        val default = Car(wheelCount = 4, maxSpeed = 200)
        fun createWithDefaultWheels(maxSpeed: Int): Car {
            return Car(wheelCount = 4, maxSpeed = 200)
        }
    }
}