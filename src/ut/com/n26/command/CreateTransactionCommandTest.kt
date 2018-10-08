package com.n26.command

import com.n26.model.*
import com.n26.store.TransactionStore
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertTrue

class CreateTransactionCommandTest {

    private val transactionStore = mock<TransactionStore>()
    private val transactionProcessor = CreateTransactionCommand(transactionStore)

    @Before
    fun setUp() {
        doNothing().`when`(transactionStore).save(any())
    }

    @Test
    fun shouldReturnOldTransactionWhenTransactionTimestampIsOlderThan60Secs() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now().minusSeconds(61))
        val request = TransactionRequest(transaction)

        val result = transactionProcessor.apply(request)

        verify(transactionStore, times(0)).save(request)
        assertTrue { result is OldTransaction }
    }

    @Test
    fun shouldReturnFutureTransactionWhenTransactionTimeStampIsInTheFuture() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now().plusSeconds(1))
        val request = TransactionRequest(transaction)

        val result = transactionProcessor.apply(request)

        verify(transactionStore, times(0)).save(request)
        assertTrue { result is FutureTransaction }
    }

    @Test
    fun shouldReturnCreatedTransactionWhenTransactionTimeStampIsValid() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now().minusSeconds(50))
        val request = TransactionRequest(transaction)

        val result = transactionProcessor.apply(request)

        verify(transactionStore, times(1)).save(request)
        assertTrue { result is CreateTransactionSuccess }
    }


}