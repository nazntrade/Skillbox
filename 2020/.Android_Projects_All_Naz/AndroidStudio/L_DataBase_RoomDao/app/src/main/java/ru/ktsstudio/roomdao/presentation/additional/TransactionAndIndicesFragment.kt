package ru.ktsstudio.roomdao.presentation.additional

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.withTransaction
import kotlinx.coroutines.launch
import ru.ktsstudio.roomdao.data.db.Database
import ru.ktsstudio.roomdao.data.db.models.Purchase
import ru.ktsstudio.roomdao.data.db.models.PurchaseStatus
import ru.ktsstudio.roomdao.data.db.models.User
import timber.log.Timber

class TransactionAndIndicesFragment : Fragment() {

    private val purchaseDao = Database.instance.purchaseDao()
    private val userDao = Database.instance.userDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchWithoutIndex()
    }

    private fun nonAtomic() {
        lifecycleScope.launch {
            val user = User(
                id = 1,
                firstName = "name",
                lastName = "lastname",
                email = "email",
                age = 20,
                avatar = null
            )
            userDao.insertUsers(listOf(user))
            error("some error")
            val purchase = Purchase(
                id = 1,
                userId = 1,
                price = 100,
                status = PurchaseStatus.CREATED
            )
            purchaseDao.insertPurchases(listOf(purchase))
        }
    }

    private fun atomicTransaction() {
        lifecycleScope.launch {
            Database.instance.withTransaction {
                val user = User(
                    id = 1,
                    firstName = "name",
                    lastName = "lastname",
                    email = "email",
                    age = 20,
                    avatar = null
                )
                userDao.insertUsers(listOf(user))
                error("some error")
                val purchase = Purchase(
                    id = 1,
                    userId = 1,
                    price = 100,
                    status = PurchaseStatus.CREATED
                )
                purchaseDao.insertPurchases(listOf(purchase))
            }
        }
    }

    private fun insertNotTransactional() {
        lifecycleScope.launch {
            val startTime = System.currentTimeMillis()
            (0..1000).forEach {
                userDao.insertUsers(
                    listOf(
                        User(
                            id = it.toLong(),
                            firstName = "name$it",
                            age = 20,
                            lastName = "lastName$it",
                            email = "email$it",
                            avatar = null
                        )
                    )
                )
            }
            Timber.d("insert time = ${System.currentTimeMillis() - startTime}")
        }
    }

    private fun insertTransactional() {
        lifecycleScope.launch {
            val startTime = System.currentTimeMillis()
            Database.instance.withTransaction {
                (0..1000).forEach {
                    userDao.insertUsers(
                        listOf(
                            User(
                                id = it.toLong(),
                                firstName = "name$it",
                                age = 20,
                                lastName = "lastName$it",
                                email = "email$it",
                                avatar = null
                            )
                        )
                    )
                }
            }
            Timber.d("insert time = ${System.currentTimeMillis() - startTime}")
        }
    }

    private fun searchWithoutIndex() {
        lifecycleScope.launch {
            userDao.removeAll()
            Database.instance.withTransaction {
                (0..100_000).forEach {
                    userDao.insertUsers(
                        listOf(
                            User(
                                id = it.toLong(),
                                firstName = "name$it",
                                lastName = "lastName$it",
                                age = 20,
                                email = "email$it",
                                avatar = null
                            )
                        )
                    )
                }
            }

            val startTime = System.currentTimeMillis()
            userDao.getUserByFirstName("name30000")
            Timber.d("search time = ${System.currentTimeMillis() - startTime}")
        }
    }
}