package com.n26.store

import com.n26.model.Statistics
import org.springframework.stereotype.Component

@Component
class StatisticsStore(var statistics: Statistics = Statistics()) {
    fun update(statistics: Statistics) {
        if (this.statistics.lastUpdate.isBefore(statistics.lastUpdate)) {
            this.statistics = statistics
        }
    }
}