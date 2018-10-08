package com.n26.controller

import com.n26.model.Statistics
import com.n26.query.StatisticsQuery
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = "/statistics")
@RestController
class StatisticsController(private val statisticsQuery: StatisticsQuery) {

    @GetMapping
    fun getStatistics(): ResponseEntity<Statistics> {
        return ResponseEntity(statisticsQuery.get(), HttpStatus.OK)
    }
}