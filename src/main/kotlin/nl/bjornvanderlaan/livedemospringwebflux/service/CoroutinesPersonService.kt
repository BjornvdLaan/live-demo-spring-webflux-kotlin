package nl.bjornvanderlaan.livedemospringwebflux.service

import nl.bjornvanderlaan.livedemospringwebflux.model.Person
import nl.bjornvanderlaan.livedemospringwebflux.repository.CoroutinesPersonRepository
import org.springframework.stereotype.Service

@Service
class CoroutinesPersonService(private val personRepository: CoroutinesPersonRepository) {
    suspend fun getPersonById(id: Long): Person? =
        personRepository.findById(id)
}