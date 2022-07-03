package nl.bjornvanderlaan.livedemospringwebflux.repository

import kotlinx.coroutines.flow.Flow
import nl.bjornvanderlaan.livedemospringwebflux.model.Cat
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CoroutinesCatRepository : CoroutineCrudRepository<Cat, Long> {
    override fun findAll(): Flow<Cat>
    override suspend fun findById(id: Long): Cat?
    override suspend fun existsById(id: Long): Boolean
    override suspend fun <S : Cat> save(entity: S): S
    override suspend fun deleteById(id: Long)
}