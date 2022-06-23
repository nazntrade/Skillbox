package com.example.hw_roomdao.data.db.models

import androidx.room.Entity

@Entity
data class Chat(
    val id: Long,
    val title: String
)
