package nl.bjornvanderlaan.livedemospringwebflux.model

data class CatDto(
    val name: String,
    val type: String,
    val age: Int,
    val ownerId: Long? = null,
    val owner: Person? = null
)

fun CatDto.toEntity(): Cat = Cat(
    name = this.name,
    type = this.type,
    age = this.age,
    ownerId = this.ownerId
)