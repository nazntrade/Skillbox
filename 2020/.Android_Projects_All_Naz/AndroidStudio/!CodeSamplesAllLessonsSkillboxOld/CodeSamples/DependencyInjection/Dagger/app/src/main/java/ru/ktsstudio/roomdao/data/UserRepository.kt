package ru.ktsstudio.roomdao.data

import ru.ktsstudio.roomdao.data.db.models.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun removeUser(userId: Long)
    suspend fun getUserById(userId: Long): User?
    suspend fun getAllUsers(): List<User>
}