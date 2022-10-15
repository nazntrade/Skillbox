package ru.ktsstudio.roomdao.data.db.models

object PurchaseContract {

    const val TABLE_NAME = "purchases"

    object Columns {
        const val ID = "id"
        const val USER_ID = "user_id"
        const val PRICE = "price"
        const val STATUS = "status"
    }

}