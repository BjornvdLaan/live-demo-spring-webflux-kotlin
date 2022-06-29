package nl.bjornvanderlaan.livedemospringwebflux.repository

import nl.bjornvanderlaan.livedemospringwebflux.model.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PersonRepository : ReactiveCrudRepository<Person, Long> {
    override fun findById(id: Long): Mono<Person>
}