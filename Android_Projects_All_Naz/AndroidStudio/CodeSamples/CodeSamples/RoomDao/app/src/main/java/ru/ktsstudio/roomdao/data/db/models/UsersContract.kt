package ru.ktsstudio.roomdao.data.db.models

object UsersContract {
    const val TABLE_NAME = "users"

    object Columns {
        const val ID = "id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val AVATAR = "avatar"
        const val AGE = "age"
    }
}