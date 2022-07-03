package nl.bjornvanderlaan.livedemospringwebflux.router

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.every
import io.mockk.slot
import nl.bjornvanderlaan.livedemospringwebflux.model.*
import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
import nl.bjornvanderlaan.livedemospringwebflux.repository.PersonRepository
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

    @MockkBean
    private lateinit var personRepository: PersonRepository

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
    fun `retrieve Cat with Owner`() {
        val inputCat = Cat(id = 1, name = "Obi", type = "Dutch Ringtail", age = 3, ownerId = 1)
        val inputPerson = Person(id = 1, name = "Peter Katlover", age = 29)

        every {
            catRepository.findById(1)
        } returns Mono.just(inputCat)
        every {
            personRepository.findById(1)
        } returns Mono.just(inputPerson)

        StepVerifier
            .create(catService.getCatById(1))
            .consumeNextWith { outputCat ->
                outputCat shouldBeEqualToComparingFields inputCat.toDto().copy(owner = inputPerson)
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