package com.n26.controller

import com.n26.model.Statistics
import com.n26.query.StatisticsQuery
import com.n26.store.StatisticsStore
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertTrue

class StatisticsControllerTest {


    private val statistics = Statistics(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE, 1L)
    private val statisticsQuery = StatisticsQuery(StatisticsStore(statistics))
    private val statisticsController = StatisticsController(statisticsQuery)

    @Test
    fun shouldReturnResponseEntityWithCurrentStatisticsWhenGetStatisticsIsCalled() {
        val responseEntity = statisticsController.getStatistics()

        assertTrue { responseEntity.body == statistics }
    }
}