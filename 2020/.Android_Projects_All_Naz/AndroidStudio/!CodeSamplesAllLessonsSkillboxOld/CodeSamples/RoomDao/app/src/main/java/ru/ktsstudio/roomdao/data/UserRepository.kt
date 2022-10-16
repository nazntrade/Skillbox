package ru.ktsstudio.roomdao.data

import android.util.Patterns
import ru.ktsstudio.roomdao.data.db.Database
import ru.ktsstudio.roomdao.data.db.models.User

class UserRepository {

    private val userDao = Database.instance.userDao()

    suspend fun saveUser(user: User) {
        if(isUserValid(user).not()) throw IncorrectFormException()
        userDao.insertUsers(listOf(user))
    }

    suspend fun updateUser(user: User) {
        if(isUserValid(user).not()) throw IncorrectFormException()
        userDao.updateUser(user)
    }

    suspend fun removeUser(userId: Long) {
        userDao.removeUserById(userId)
    }

    suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    private fun isUserValid(user: User): Boolean {
        return user.firstName.isNotBlank() &&
            user.lastName.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
    }

}