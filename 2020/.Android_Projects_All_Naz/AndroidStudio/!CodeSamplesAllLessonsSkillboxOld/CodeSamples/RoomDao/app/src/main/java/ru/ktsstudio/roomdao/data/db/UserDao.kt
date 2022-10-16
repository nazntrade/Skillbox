package ru.ktsstudio.roomdao.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.ktsstudio.roomdao.data.db.models.User
import ru.ktsstudio.roomdao.data.db.models.UserWithRelations
import ru.ktsstudio.roomdao.data.db.models.UsersContract

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME}")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME}")
    suspend fun getAllUsersWithRelations(): List<UserWithRelations>

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.ID} = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.FIRST_NAME} = :firstName")
    suspend fun getUserByFirstName(firstName: String): User?

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.LAST_NAME} = :lastName")
    suspend fun getUserByLastName(lastName: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun removeUser(user: User)

    @Query("DELETE FROM ${UsersContract.TABLE_NAME} WHERE ${UsersContract.Columns.ID} = :userId")
    suspend fun removeUserById(userId: Long)

    @Query("DELETE FROM ${UsersContract.TABLE_NAME}")
    suspend fun removeAll()

}