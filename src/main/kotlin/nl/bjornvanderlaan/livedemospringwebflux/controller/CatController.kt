package nl.bjornvanderlaan.livedemospringwebflux.controller

import nl.bjornvanderlaan.livedemospringwebflux.model.CatDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/cats")
class CatController(private val catService: CatService) {

    @GetMapping
    fun getCats(): ResponseEntity<Flux<CatDto>> =
        catService
            .getAllCats()
            .let {
                ResponseEntity.ok().body(it)
            }

    @GetMapping("/{id}")
    fun getCat(@PathVariable id: Long): Mono<ResponseEntity<CatDto>> =
        catService
            .getCatById(id)
            .map { cat ->
                ResponseEntity.ok().body(cat)
            }
            .defaultIfEmpty(
                ResponseEntity.notFound().build()
            )

    @PostMapping
    fun createCat(@RequestBody cat: CatDto): Mono<ResponseEntity<CatDto>> =
        catService
            .addNewCat(cat)
            .map {
                ResponseEntity.ok().body(cat)
            }
            .defaultIfEmpty(
                ResponseEntity.notFound().build()
            )

    @PutMapping("/{id}")
    fun updateCat(@PathVariable id: Long, @RequestBody updatedCat: CatDto): Mono<ResponseEntity<CatDto>> =
        catService
            .updateExistingCat(id, updatedCat)
            .map { cat ->
                ResponseEntity.ok().body(cat)
            }
            .onErrorReturn(ResponseEntity.badRequest().build())

    @DeleteMapping("/{id}")
    fun deleteCat(@PathVariable id: Long): Mono<ResponseEntity<Void>> =
        catService
            .deleteExistingCat(id)
            .map { ResponseEntity.noContent().build<Void>() }
            .onErrorReturn(ResponseEntity.notFound().build())
            .defaultIfEmpty(ResponseEntity.notFound().build())
}