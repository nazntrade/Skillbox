package com.skillbox.activity

import java.io.Serializable

data class CounterState(
    val count: Int,
    val str: String
): Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID = 1L
    }
}