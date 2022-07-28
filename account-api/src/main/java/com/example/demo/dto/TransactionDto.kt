package com.example.demo.dto

import com.example.demo.model.Transaction
import com.example.demo.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(


        val id: String?,
        val transactionType: TransactionType? = TransactionType.INITIAL,
        val amount: BigDecimal?,
        val transactionDate: LocalDateTime?

        )
