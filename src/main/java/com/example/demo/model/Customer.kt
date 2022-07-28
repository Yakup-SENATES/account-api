package com.example.demo.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.math.BigInteger
import javax.persistence.*

@Entity
data class Customer(

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String?=null,

        val name: String?="",
        val surname: String?="",


        @OneToMany(mappedBy = "customer" , fetch = FetchType.EAGER)
        val accounts: Set<Account>?=null

) {
        constructor(name: String, surname: String) : this("", name, surname, HashSet())


        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
                other as Customer

                return id != null && id == other.id
        }

        override fun hashCode(): Int = javaClass.hashCode()

        @Override
        override fun toString(): String {
                return this::class.simpleName + "(id = $id , name = $name , surname = $surname )"
        }
}
