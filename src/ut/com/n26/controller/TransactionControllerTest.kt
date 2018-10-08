package com.n26.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.n26.model.*
import com.n26.command.CreateTransactionCommand
import com.n26.command.DeleteTransactionCommand
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertTrue

class TransactionControllerTest {

    private val createTransactionCommand: CreateTransactionCommand = mock()
    private val deleteTransactionCommand: DeleteTransactionCommand = mock()
    private val transactionController = TransactionController(createTransactionCommand, deleteTransactionCommand)

    @Test
    fun `Given a create transaction request When transaction is successfully processed Then 201 response code should be returned`() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now())

        whenever(createTransactionCommand.apply(any())).thenReturn(CreateTransactionSuccess(transaction))

        val response = transactionController.createTransaction(transaction)

        assertTrue { response.statusCode.value() == 201 }
    }

    @Test
    fun `Given a create transaction request When transaction is older than 60 sec Then 204 response code should be returned`() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now().minusSeconds(60))

        whenever(createTransactionCommand.apply(any())).thenReturn(OldTransaction(transaction))

        val response = transactionController.createTransaction(transaction)

        assertTrue { response.statusCode.value() == 204 }
    }

    @Test
    fun `Given create transaction request When transaction is in the future Then 422 response code should be returned`() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now().plusMillis(60))

        whenever(createTransactionCommand.apply(any())).thenReturn(FutureTransaction(transaction))

        val response = transactionController.createTransaction(transaction)

        assertTrue { response.statusCode.value() == 422 }
    }

    @Test
    fun `Given create transaction request When an error occurs Then 500 response code should be returned`() {
        val transaction = Transaction(BigDecimal.ONE, Instant.now())

        whenever(createTransactionCommand.apply(any())).thenReturn(CreateTransactionFailure(transaction, Exception()))

        val response = transactionController.createTransaction(transaction)

        assertTrue { response.statusCode.value() == 500 }
    }

    @Test
    fun `Given a delete transactions request When transactions are deleted successfully Then 204 response code should be returned`() {

        whenever(deleteTransactionCommand.process()).thenReturn(DeleteTransactionsSuccess())

        val response = transactionController.deleteAllTransactions()

        assertTrue { response.statusCode.value() == 204 }
    }

    @Test
    fun `Given a delete transactions request When transactions deletion fails Then 500 response code should be returned`() {

        whenever(deleteTransactionCommand.process()).thenReturn(DeleteTransactionsFailure(Exception()))

        val response = transactionController.deleteAllTransactions()

        assertTrue { response.statusCode.value() == 500 }
    }
}