package com.n26.command

import com.n26.model.*
import com.n26.store.TransactionStore
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.function.Function

@Component
open class CreateTransactionCommand(private val transactionStore: TransactionStore)
    : Function<TransactionRequest, TransactionCreationResult> {

    override fun apply(request: TransactionRequest): TransactionCreationResult =
            when {
                request.transaction.timestamp.isBefore(Instant.now().minusSeconds(60)) ->
                    OldTransaction(request.transaction)
                request.transaction.timestamp.isAfter(Instant.now()) -> FutureTransaction(request.transaction)
                else -> {
                    transactionStore.save(request)
                    CreateTransactionSuccess(request.transaction)
                }
            }
}