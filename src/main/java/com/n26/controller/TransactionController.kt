package com.n26.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.n26.model.*
import com.n26.command.CreateTransactionCommand
import com.n26.command.DeleteTransactionCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(private val createTransactionCommand: CreateTransactionCommand,
                            private val deleteTransactionCommand: DeleteTransactionCommand) {

    @PostMapping
    fun createTransaction(@RequestBody transaction: Transaction): ResponseEntity<Unit> {
        val result = createTransactionCommand.apply(TransactionRequest(transaction))
        return when (result) {
            is OldTransaction -> ResponseEntity(HttpStatus.NO_CONTENT)
            is FutureTransaction -> ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
            is CreateTransactionSuccess -> ResponseEntity(HttpStatus.CREATED)
            is CreateTransactionFailure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping
    fun deleteAllTransactions(): ResponseEntity<Unit> {
        val result = deleteTransactionCommand.process()
        return when (result) {
            is DeleteTransactionsSuccess -> ResponseEntity(HttpStatus.NO_CONTENT)
            is DeleteTransactionsFailure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = [(InvalidFormatException::class)])
    fun exceptionHandler() {}
}