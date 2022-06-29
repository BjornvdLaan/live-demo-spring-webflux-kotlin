package nl.bjornvanderlaan.livedemospringwebflux.repository

import nl.bjornvanderlaan.livedemospringwebflux.model.Person
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CoroutinesPersonRepository : CoroutineCrudRepository<Person, Long> {
    override suspend fun findById(id: Long): Person?
}