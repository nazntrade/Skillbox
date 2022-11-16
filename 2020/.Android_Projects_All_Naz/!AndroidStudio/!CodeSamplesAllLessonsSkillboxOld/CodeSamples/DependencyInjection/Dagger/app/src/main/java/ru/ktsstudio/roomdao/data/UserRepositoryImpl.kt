package ru.ktsstudio.roomdao.data

import android.util.Patterns
import ru.ktsstudio.roomdao.data.db.Database
import ru.ktsstudio.roomdao.data.db.UserDao
import ru.ktsstudio.roomdao.data.db.models.User
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepository {

    init {
        Timber.d("init UserRepositoryImpl")
    }

    override suspend fun saveUser(user: User) {
        if(isUserValid(user).not()) throw IncorrectFormException()
        userDao.insertUsers(listOf(user))
    }

    override suspend fun updateUser(user: User) {
        if(isUserValid(user).not()) throw IncorrectFormException()
        userDao.updateUser(user)
    }

    override suspend fun removeUser(userId: Long) {
        userDao.removeUserById(userId)
    }

    override suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    private fun isUserValid(user: User): Boolean {
        return user.firstName.isNotBlank() &&
            user.lastName.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
    }

}