package nl.bjornvanderlaan.livedemospringwebflux.repository

import nl.bjornvanderlaan.livedemospringwebflux.model.Cat
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CatRepository : ReactiveCrudRepository<Cat, Long> {
    override fun findAll(): Flux<Cat>
    override fun findById(id: Long): Mono<Cat>
    override fun existsById(id: Long): Mono<Boolean>
    override fun <S : Cat> save(entity: S): Mono<S>
    override fun deleteById(id: Long): Mono<Void>
}