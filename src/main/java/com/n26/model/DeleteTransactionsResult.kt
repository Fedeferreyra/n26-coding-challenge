package com.n26.model

sealed class DeleteTransactionsResult
class DeleteTransactionsSuccess : DeleteTransactionsResult()
class DeleteTransactionsFailure(val exception: Exception) : DeleteTransactionsResult()

