package nl.bjornvanderlaan.livedemospringwebflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Cat(
    @Id val id: Long? = null,
    val ownerId: Long? = null,
    val name: String,
    val type: String,
    val age: Int
)

fun Cat.toDto(): CatDto = CatDto(
    name = this.name,
    type = this.type,
    age = this.age,
    ownerId = this.ownerId
)