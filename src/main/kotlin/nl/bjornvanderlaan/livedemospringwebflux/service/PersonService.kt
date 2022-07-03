package nl.bjornvanderlaan.livedemospringwebflux.service

import nl.bjornvanderlaan.livedemospringwebflux.model.Person
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PersonService(private val personRepository: PersonRepository) {
    fun getPersonById(id: Long): Mono<Person> =
        personRepository.findById(id)
}