package nl.bjornvanderlaan.livedemospringwebflux.service

import nl.bjornvanderlaan.livedemospringwebflux.model.Person
import nl.bjornvanderlaan.livedemospringwebflux.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
    suspend fun getPersonById(id: Long): Person? =
        personRepository.findById(id)
}