package com.n26.store

import com.n26.model.Transaction
import com.n26.model.TransactionRequest
import com.n26.model.UpdateNotification
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import kotlin.test.assertTrue

class TransactionStoreTest {

    private val transactionStore = TransactionStore()
    private val observer = mock<Observer>()

    @Before
    fun setUp() {
        transactionStore.addObserver(observer)
    }

    @Test
    fun shouldNotifyObserversWhenTransactionIsAdded() {
        transactionStore.save(TransactionRequest(Transaction(BigDecimal.ONE, Instant.now())))

        verify(observer, times(1)).update(any(), any())
    }

    @Test
    fun shouldNotifyObserversWithAnEmptyListWhenTransactionsAreDeleted() {
        transactionStore.purgeEntireStore()

        verify(observer, times(1)).update(any(), check{
            it as UpdateNotification
            assertTrue { it.transactions.isEmpty() }
        })
    }

    @Test
    fun givenOneOldTransactionAndOneNotSoOldWhenPurgeIsActivatedThenShouldNotifyObserversWithOneItemInTheList() {
        val transactionToBePurged = Transaction(BigDecimal.ONE, Instant.now().minusSeconds(69))
        val transactionToNotBePurged = Transaction(BigDecimal.ONE, Instant.now().plusSeconds(59))
        transactionStore.save(TransactionRequest(transactionToBePurged))
        transactionStore.save(TransactionRequest(transactionToNotBePurged))

        transactionStore.expireOldTransactions()

        verify(observer, times(3)).update(any(), check{
            it as UpdateNotification
            assertTrue { it.transactions.size == 1 }
        })
    }
}