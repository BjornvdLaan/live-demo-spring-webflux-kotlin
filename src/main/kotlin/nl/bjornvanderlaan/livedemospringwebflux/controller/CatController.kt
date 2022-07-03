package nl.bjornvanderlaan.livedemospringwebflux.controller

import kotlinx.coroutines.flow.Flow
import nl.bjornvanderlaan.livedemospringwebflux.model.CatDto
import nl.bjornvanderlaan.livedemospringwebflux.service.CatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cats")
class CatController(private val catService: CatService) {

    @GetMapping
    fun getCats(): ResponseEntity<Flow<CatDto>> =
        catService
            .getAllCats()
            .let { cats -> ResponseEntity.ok().body(cats) }


    @GetMapping("/{id}")
    suspend fun getCat(@PathVariable id: Long): ResponseEntity<CatDto> =
        catService
            .getCatById(id)
            ?.let { cat -> ResponseEntity.ok().body(cat) }
            ?: ResponseEntity.notFound().build()


    @PostMapping
    suspend fun createCat(@RequestBody cat: CatDto): ResponseEntity<CatDto> =
        catService
            .addNewCat(cat)
            .let { savedCat -> ResponseEntity.ok().body(savedCat) }


    @PutMapping("/{id}")
    suspend fun updateCat(@PathVariable id: Long, @RequestBody updatedCat: CatDto): ResponseEntity<CatDto> =
        if (catService.getCatById(id) != null) {
            val savedCat = catService.updateExistingCat(id, updatedCat)
            ResponseEntity.ok().body(savedCat)
        } else {
            ResponseEntity.unprocessableEntity().build()
        }


    @DeleteMapping("/{id}")
    suspend fun deleteCat(@PathVariable id: Long): ResponseEntity<Void> =
        catService.getCatById(id)?.let {
            catService.deleteExistingCat(id)
            ResponseEntity.noContent().build()
        } ?: ResponseEntity.notFound().build()
}