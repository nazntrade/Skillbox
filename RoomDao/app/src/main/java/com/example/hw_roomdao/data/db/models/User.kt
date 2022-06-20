package com.example.hw_roomdao.data.db.models

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?,
    val age: Int

)