package com.example.demo.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Transaction(

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = null,

        val transactionType: TransactionType? = TransactionType.INITIAL,
        val amount: BigDecimal?= BigDecimal.ZERO,
        val transactionDate: LocalDateTime? = LocalDateTime.now(),


        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_id", nullable = false)
        val account: Account? = null


){
    constructor(amount: BigDecimal, account: Account) : this(
        id = null,
        amount = amount,
        account = account
)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Transaction

        if (id != other.id) return false
        if (transactionType != other.transactionType) return false
        if (amount != other.amount) return false
        if (transactionDate != other.transactionDate) return false
        if (account != other.account) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (transactionType?.hashCode() ?: 0)
        result = 31 * result + (amount?.hashCode() ?: 0)
        result = 31 * result + (transactionDate?.hashCode() ?: 0)
        return result
    }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , transactionType = $transactionType , amount = $amount , transactionDate = $transactionDate )"
    }

}

enum class TransactionType {
    INITIAL, TRANSFER
}
