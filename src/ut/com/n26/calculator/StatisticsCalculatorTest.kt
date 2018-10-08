package com.n26.calculator

import com.n26.model.Statistics
import com.n26.model.Transaction
import com.n26.model.UpdateNotification
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertTrue

class StatisticsCalculatorTest {

    private val statisticsCalculator = StatisticsCalculator()

    @Test
    fun shouldReturnDefaultStatisticsWhenListIsEmpty() {
        val statistics = statisticsCalculator.apply(UpdateNotification(transactions = listOf()))

        assertTrue { statistics.count == 0L }
    }

    @Test
    fun shouldHaveTransactionValueAsSumMaxMinAndAvgWhenListHasOnlyOneItem() {
        val transactions = listOf(Transaction(BigDecimal.TEN.setScale(2), Instant.now()))

        val statistics = statisticsCalculator.apply(UpdateNotification(transactions = transactions))

        assertTrue { statistics.sum == transactions.first().amount }
        assertTrue { statistics.max == transactions.first().amount }
        assertTrue { statistics.min == transactions.first().amount }
        assertTrue { statistics.avg == transactions.first().amount }
        assertTrue { statistics.count == transactions.size.toLong() }
    }

    @Test
    fun shouldHave10asMAxAnd1AsMinWhenListHas2ItemsWithValues10and1() {
        val maxValueTransaction = Transaction(BigDecimal.TEN, Instant.now())
        val minValueTransaction = Transaction(BigDecimal.ONE, Instant.now())
        val transactions = listOf(maxValueTransaction, minValueTransaction)

        val statistics = statisticsCalculator.apply(UpdateNotification(transactions = transactions))


        assertTrue { statistics.max == maxValueTransaction.amount }
        assertTrue { statistics.min == minValueTransaction.amount }
        assertTrue { statistics.avg == BigDecimal.valueOf(550L, 2) }
        assertTrue { statistics.sum == BigDecimal.valueOf(11) }
        assertTrue { statistics.count == transactions.size.toLong() }
    }

}