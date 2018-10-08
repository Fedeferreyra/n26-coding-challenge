package com.n26.query

import com.n26.model.Statistics
import com.n26.store.StatisticsStore
import org.springframework.stereotype.Component

@Component
class StatisticsQuery(private val statisticsStore: StatisticsStore) {

    fun get(): Statistics {
        val statistics = statisticsStore.statistics

        println(statistics.callNumber)

        return statistics
    }
}