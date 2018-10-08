package com.n26.model

import java.math.BigDecimal
import java.time.Instant

data class Transaction(val amount: BigDecimal,
                       val timestamp: Instant)