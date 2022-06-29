package nl.bjornvanderlaan.livedemospringwebflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Person(
    @Id val id: Long? = null,
    val name: String,
    val age: Int
)