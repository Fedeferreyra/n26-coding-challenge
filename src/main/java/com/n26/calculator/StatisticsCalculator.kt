package com.n26.calculator

import com.n26.model.Statistics
import com.n26.model.UpdateNotification
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.util.function.Function

@Component
class StatisticsCalculator : Function<UpdateNotification, Statistics> {

    override fun apply(notification: UpdateNotification): Statistics =
            if (notification.transactions.isEmpty()) {
                Statistics(lastUpdate = notification.updateInstant)
            } else {
                var max = BigDecimal.valueOf(Long.MIN_VALUE)
                var min = BigDecimal.valueOf(Long.MAX_VALUE)
                var avg = BigDecimal.ZERO
                var sum = BigDecimal.ZERO
                var count = 0L

                notification.transactions.forEach {
                    if (it.timestamp.isAfter(Instant.now().minusSeconds(59))) {
                        count++
                        sum = sum.add(it.amount)
                        if (it.amount < min) min = it.amount
                        if (it.amount > max) max = it.amount
                    }
                }

                println("Count: $count")
                avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP)
                Statistics(sum, avg, max, min, count, notification.updateInstant)
            }


}