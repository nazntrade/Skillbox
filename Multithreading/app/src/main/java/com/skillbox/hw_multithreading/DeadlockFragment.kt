package com.skillbox.hw_multithreading

//Nowadays, this problem itself is solved under the hood. But it was interesting to break the head.

import android.util.Log
import androidx.fragment.app.Fragment

class DeadlockFragment : Fragment() {

    override fun onResume() {
        super.onResume()

        val friend1 = Person("Robert")
        val friend2 = Person("Andrew")
        Thread { friend1.throwBallTo(friend2) }.start()
        Thread { friend2.throwBallTo(friend1) }.start()
    }

    data class Person(val name: String) {

        private var i = 0

        @Synchronized // remove @Synchronized in order for the program to work
        fun throwBallTo(friend: Person) {
            i++
            Log.d(
                "Deadlock",
                "$name throw to ${friend.name} on thread ${Thread.currentThread().name}. Increment $i"
            )
            Thread.sleep(500)
            friend.throwBack(this)
        }

        @Synchronized // remove @Synchronized in order for the program to work
        private fun throwBack(friend: Person) {
            Log.d(
                "Deadlock",
                "$name throw back to ${friend.name} on thread " +
                        Thread.currentThread().name
            )
            friend.throwBallTo(this)
        }
    }
}