package nl.bjornvanderlaan.livedemospringwebflux.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.bjornvanderlaan.livedemospringwebflux.model.CatDto
import nl.bjornvanderlaan.livedemospringwebflux.model.toDto
import nl.bjornvanderlaan.livedemospringwebflux.model.toEntity
import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
import org.springframework.stereotype.Service

@Service
class CatService(
    private val catRepository: CatRepository,
    private val personService: PersonService
) {
    fun getAllCats(): Flow<CatDto> =
        catRepository
            .findAll()
            .map {
                it.toDto()
            }

    suspend fun getCatById(id: Long): CatDto? {
        val cat = catRepository.findById(id)
        val owner = personService.getPersonById(id)

        if (cat == null)
            return null

        return if (owner != null) {
            cat.toDto().copy(owner = owner)
        } else {
            cat.toDto()
        }
    }

    suspend fun addNewCat(cat: CatDto): CatDto =
        catRepository
            .save(cat.toEntity())
            .toDto()

    suspend fun updateExistingCat(id: Long, updatedCat: CatDto): CatDto? =
        catRepository
            .findById(id)
            ?.let { existingCat ->
                catRepository.save(updatedCat.toEntity().copy(id = existingCat.id)).toDto()
            }
            ?: throw NoSuchElementException()

    suspend fun deleteExistingCat(id: Long): CatDto? =
        catRepository
            .findById(id)
            ?.let { existingCat ->
                catRepository.deleteById(id)
                existingCat.toDto()
            }
            ?: throw NoSuchElementException()
}