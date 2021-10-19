package ru.ktsstudio.roomdao.presentation.additional

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.ktsstudio.roomdao.data.db.Database
import ru.ktsstudio.roomdao.data.db.models.Purchase
import ru.ktsstudio.roomdao.data.db.models.PurchaseStatus
import timber.log.Timber

class FkFragment : Fragment() {

    private val purhcaseDao = Database.instance.purchaseDao()
    private val userDao = Database.instance.userDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savePurchases()
        testRelations()
    }

    private fun savePurchases() {
        val purchases = listOf(
            Purchase(
                id = 0,
                userId = 1,
                price = 100,
                status = PurchaseStatus.CREATED
            ),
            Purchase(
                id = 0,
                userId = 1,
                price = 10,
                status = PurchaseStatus.CREATED
            )
        )
        lifecycleScope.launch {
            purhcaseDao.insertPurchases(purchases)
        }
    }

    private fun testRelations() {
        lifecycleScope.launch {
            val userWithRelations = userDao.getAllUsersWithRelations()
            Timber.d(userWithRelations.toString())
        }
    }
}