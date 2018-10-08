package com.n26.model

import java.time.Instant

data class UpdateNotification(val updateInstant: Instant = Instant.now(),
                              val transactions: List<Transaction>,
                              val type: String = "Default")
