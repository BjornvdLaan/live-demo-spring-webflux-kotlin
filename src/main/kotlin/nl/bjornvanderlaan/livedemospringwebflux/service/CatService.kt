package nl.bjornvanderlaan.livedemospringwebflux.service

import nl.bjornvanderlaan.livedemospringwebflux.model.CatDto
import nl.bjornvanderlaan.livedemospringwebflux.model.toDto
import nl.bjornvanderlaan.livedemospringwebflux.model.toEntity
import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CatService(private val catRepository: CatRepository, private val personService: PersonService) {
    fun getAllCats(): Flux<CatDto> =
        catRepository
            .findAll()
            .map { it.toDto() }

    fun getCatById(id: Long): Mono<CatDto> =
        catRepository
            .findById(id)
            .flatMap { cat ->
                personService
                    .getPersonById(id)
                    .map { cat.toDto().copy(ownerId = it.id, owner = it) }
                    .switchIfEmpty { Mono.just(cat.toDto()) }
            }

    fun addNewCat(cat: CatDto): Mono<CatDto> =
        catRepository
            .save(cat.toEntity())
            .map { it.toDto() }

    fun updateExistingCat(id: Long, updatedCat: CatDto) =
        catRepository
            .findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException()))
            .flatMap { existingCat ->
                catRepository.save(updatedCat.toEntity().copy(id = existingCat.id))
            }
            .map { it.toDto() }

    fun deleteExistingCat(id: Long): Mono<CatDto> =
        catRepository
            .findById(id)
            .flatMap {
                catRepository.deleteById(id)
                Mono.just(it.toDto())
            }
            .switchIfEmpty(Mono.error(NoSuchElementException()))
}