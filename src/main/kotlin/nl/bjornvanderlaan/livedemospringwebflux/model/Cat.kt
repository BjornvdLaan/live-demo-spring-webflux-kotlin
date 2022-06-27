package nl.bjornvanderlaan.livedemospringwebflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Cat(
    @Id var id: Long? = null,
    val name: String,
    val type: String,
    val age: Int
)
