package com.n26.command

import com.n26.calculator.StatisticsCalculator
import com.n26.model.Statistics
import com.n26.model.Transaction
import com.n26.model.UpdateNotification
import com.n26.store.StatisticsStore
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertTrue

class UpdateStatisticsCommandTest {

    private lateinit var statisticsStore: StatisticsStore
    private lateinit var updateStatisticsCommand: UpdateStatisticsCommand


    @Test
    fun shouldCalculateAndUpdateStatisticsWhenUpdateMethodIsCalledWithNotEmptyList() {
        statisticsStore = StatisticsStore()
        updateStatisticsCommand = UpdateStatisticsCommand(StatisticsCalculator(), statisticsStore)

        updateStatisticsCommand.update(UpdateNotification(Instant.now().plusSeconds(1), listOf(Transaction(BigDecimal.TEN, Instant.now()))))

        assertTrue { statisticsStore.statistics.count == 1L }
    }

    @Test
    fun shouldUpdateToDefaultStatisticsWhenUpdateMethodIsCalledWithEmptyList() {
        statisticsStore = StatisticsStore(Statistics())
        updateStatisticsCommand = UpdateStatisticsCommand(StatisticsCalculator(), statisticsStore)

        updateStatisticsCommand.update(UpdateNotification(transactions = listOf()))

        assertTrue { statisticsStore.statistics.count == 0L }
    }
}