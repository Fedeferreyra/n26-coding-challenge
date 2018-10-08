package com.n26.model

import java.time.Instant

data class TransactionRequest(val transaction: Transaction,
                              val transactionInstant: Instant = Instant.now())