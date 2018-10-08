package com.n26.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.time.Instant

data class Statistics(val sum: BigDecimal = BigDecimal.ZERO,
                      val avg: BigDecimal = BigDecimal.ZERO,
                      val max: BigDecimal = BigDecimal.ZERO,
                      val min: BigDecimal = BigDecimal.ZERO,
                      val count: Long = 0L,
                      @JsonIgnore val lastUpdate: Instant = Instant.now())