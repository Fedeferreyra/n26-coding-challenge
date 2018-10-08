package com.n26.command

import com.n26.calculator.StatisticsCalculator
import com.n26.model.UpdateNotification
import com.n26.store.StatisticsStore
import org.springframework.stereotype.Component

@Component
open class UpdateStatisticsCommand(private val statisticsCalculator: StatisticsCalculator,
                                   private val statisticsStore: StatisticsStore) {

    open fun update(updateNotification: UpdateNotification) {
        val statistics = statisticsCalculator.apply(updateNotification)
        statisticsStore.update(statistics)
    }
}