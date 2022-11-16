package ru.ktsstudio.roomdao.data.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithRelations(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = UsersContract.Columns.ID,
        entityColumn = PurchaseContract.Columns.USER_ID
    )
    val purchases: List<Purchase>
)