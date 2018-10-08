package com.n26.command

import com.n26.model.DeleteTransactionsResult
import com.n26.model.DeleteTransactionsSuccess
import com.n26.store.TransactionStore
import org.springframework.stereotype.Component

@Component
open class DeleteTransactionCommand(private val transactionStore: TransactionStore) {

    open fun process(): DeleteTransactionsResult {
        transactionStore.purgeEntireStore()
        return DeleteTransactionsSuccess()
    }
}