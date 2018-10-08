package com.n26.calculator

import com.n26.model.Statistics
import com.n26.model.UpdateNotification
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.function.Function

@Component
class StatisticsCalculator : Function<UpdateNotification, Statistics> {

    var counter = 0

    override fun apply(notification: UpdateNotification): Statistics =
            if (notification.transactions.isEmpty()) {
                Statistics(lastUpdate = notification.updateInstant)
            } else {
                if (notification.type == "SAVE") {
                    counter++
                    println("LLamadas a Statistics from save:: $counter")
                }
                var max = BigDecimal.valueOf(Long.MIN_VALUE)
                var min = BigDecimal.valueOf(Long.MAX_VALUE)
                var avg = BigDecimal.ZERO
                var sum = BigDecimal.ZERO
                var count = notification.transactions.size.toLong()

                notification.transactions.forEach {
                    sum = sum.add(it.amount)
                    if (it.amount < min) min = it.amount
                    if (it.amount > max) max = it.amount
                }

                avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP)
                Statistics(sum, avg, max, min, count, notification.updateInstant, counter)
            }


}