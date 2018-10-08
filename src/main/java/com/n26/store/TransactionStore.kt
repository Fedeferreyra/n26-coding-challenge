package com.n26.store

import com.n26.model.Transaction
import com.n26.model.TransactionRequest
import com.n26.model.UpdateNotification
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList


@Component
open class TransactionStore(private var transactionsList: MutableList<Transaction>
                            = CopyOnWriteArrayList(ArrayList<Transaction>())) : Observable() {

    @Scheduled(fixedRate = 1000L)
    fun purgeOldTransactions() {
        purgeFrom(Instant.now().minusSeconds(60))
    }

    open fun save(request: TransactionRequest) {
        transactionsList.add(request.transaction)
        notifyChanges(request.transactionInstant, "SAVE")
    }

    fun purgeEntireStore() {
        transactionsList.clear()
        notifyChanges(Instant.now())
    }

    private fun purgeFrom(instant: Instant) {
        transactionsList.removeAll { it.timestamp.isBefore(instant) }
        notifyChanges(Instant.now())
    }

    private fun notifyChanges(updateInstant: Instant, type: String = "default") {
        setChanged()
        notifyObservers(UpdateNotification(updateInstant, transactionsList, type))
    }
}
