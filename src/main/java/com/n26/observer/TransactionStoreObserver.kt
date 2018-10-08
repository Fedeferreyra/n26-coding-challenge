package com.n26.observer

import com.n26.command.UpdateStatisticsCommand
import com.n26.model.UpdateNotification
import com.n26.store.TransactionStore
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
open class TransactionStoreObserver(private val transactionStore: TransactionStore,
                                    private val updateStatisticsCommand: UpdateStatisticsCommand) : Observer {


    @PostConstruct
    fun init(){
        transactionStore.addObserver(this)
    }

    @Async
    override fun update(o: Observable?, arg: Any?) {
        val updateNotification = arg as UpdateNotification
        updateStatisticsCommand.update(updateNotification)
    }

}