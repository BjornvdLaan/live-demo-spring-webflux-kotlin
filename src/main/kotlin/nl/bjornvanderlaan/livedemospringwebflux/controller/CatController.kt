package nl.bjornvanderlaan.livedemospringwebflux.controller

import nl.bjornvanderlaan.livedemospringwebflux.model.Cat
import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/cats")
class CatController(private val catRepository: CatRepository) {

    @GetMapping("/{id}")
    fun getCat(@PathVariable id: Long): Mono<ResponseEntity<Cat>> =
        catRepository
            .findById(id)
            .map { cat ->
                ResponseEntity.ok().body(cat)
            }
            .defaultIfEmpty(
                ResponseEntity.notFound().build()
            )

    @GetMapping
    fun getCats(): Flux<Cat> =
        catRepository.findAll()

    @PostMapping
    fun createCat(@RequestBody cat: Cat): Mono<ResponseEntity<Cat>> =
        catRepository
            .save(cat)
            .map {
                ResponseEntity.ok().body(cat)
            }
            .defaultIfEmpty(
                ResponseEntity.notFound().build()
            )

    @PutMapping("/{id}")
    fun updateCat(@PathVariable id: Long, @RequestBody updatedCat: Cat): Mono<ResponseEntity<Cat>> =
        catRepository
            .findById(id)
            .flatMap { existingCat ->
                catRepository.save(updatedCat.copy(id = existingCat.id))
            }
            .map { cat ->
                ResponseEntity.ok().body(cat)
            }

    @DeleteMapping("/{id}")
    fun deleteCat(@PathVariable id: Long): Mono<ResponseEntity<Void>> =
        catRepository
            .findById(id)
            .filter { cat -> cat.id != null }
            .flatMap { cat ->
                catRepository.deleteById(cat.id!!)
                    .then(Mono.just(ResponseEntity.noContent().build<Void>()))
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
}