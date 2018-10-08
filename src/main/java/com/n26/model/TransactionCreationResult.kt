package com.n26.model

sealed class TransactionCreationResult(transaction: Transaction)

data class CreateTransactionSuccess(val transaction: Transaction) : TransactionCreationResult(transaction)
data class OldTransaction(val transaction: Transaction) : TransactionCreationResult(transaction)
data class FutureTransaction(val transaction: Transaction) : TransactionCreationResult(transaction)
data class CreateTransactionFailure(val transaction: Transaction,
                                    val exception: Exception) : TransactionCreationResult(transaction)

