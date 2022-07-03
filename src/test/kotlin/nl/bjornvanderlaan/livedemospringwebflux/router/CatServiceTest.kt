package nl.bjornvanderlaan.livedemospringwebflux.router

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.every
import io.mockk.slot
import nl.bjornvanderlaan.livedemospringwebflux.model.Cat
import nl.bjornvanderlaan.livedemospringwebflux.model.CatDto
import nl.bjornvanderlaan.livedemospringwebflux.model.toEntity
import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
import nl.bjornvanderlaan.livedemospringwebflux.service.CatService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class CatServiceTest {

    @MockkBean
    private lateinit var catRepository: CatRepository
    @Autowired
    private lateinit var catService: CatService

    @Test
    fun `should retrieve all cats`() {
        val firstCat = CatDto(
            name = "Obi",
            type = "Dutch Ringtail",
            age = 3
        )
        val secondCat = CatDto(
            name = "Wan",
            type = "Japanese Bobtail",
            age = 5
        )

        every {
            catRepository.findAll()
        } returns
                Flux.just(firstCat.toEntity(), secondCat.toEntity())

        StepVerifier
            .create(catService.getAllCats())
            .consumeNextWith { outputCat ->
                outputCat shouldBeEqualToComparingFields firstCat
            }
            .consumeNextWith { outputCat ->
                outputCat shouldBeEqualToComparingFields secondCat
            }
            .verifyComplete()
    }

    @Test
    fun `should add a new cat`() {
        val catSlot = slot<Cat>()
        every {
            catRepository.save(capture(catSlot))
        } coAnswers {
            Mono.just(catSlot.captured)
        }

        val inputCat = CatDto(
            name = "Obi",
            type = "Dutch Ringtail",
            age = 3
        )

        StepVerifier
            .create(catService.addNewCat(inputCat))
            .consumeNextWith { outputCat ->
                inputCat shouldBeEqualToComparingFields outputCat
            }
            .verifyComplete()
    }
}